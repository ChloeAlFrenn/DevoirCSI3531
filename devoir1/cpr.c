/*------------------------------------------------------------
Fichier: cpr.c

Nom: Chloe Al-Frenn, Chada Bendriss
Numero d'etudiant:  300211508, 300266679

Description: Ce programme contient le code pour la creation
			 d'un processus enfant et y attacher un tuyau.
		 L'enfant envoyera des messages par le tuyau
		 qui seront ensuite envoyes a la sortie standard.

Explication du processus zombie
(point 5 de "A completer" dans le devoir):un processus zombie apparaît
lorsque le processus enfant (cpr n-1) se termine mais que son parent
n'a pas encore terminé lors du sleep(10). Puisque le parent n’a pas de
wait il n’attend pas et ne sait pas quand l’enfant termine alors les
ressources de l’enfant ne sont pas relâchée et le pid de l’enfant devient
associée a <defunct>.  Dans ce cas, cpr n-1 devient un zombie après avoir
terminé, et reste dans cet état jusqu'à ce que cpr n se termine pour
relâcher les ressources de l’enfant.

	(s.v.p. completez cette partie);

-------------------------------------------------------------*/
#include <stdio.h>
#include <sys/select.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_SIZE 50
#define READ_END 0
#define WRITE_END 1

/* Prototype */
void creerEnfantEtLire(int);

/*-------------------------------------------------------------
Function: main
Arguments:
	int ac	- nombre d'arguments de la commande
	char **av - tableau de pointeurs aux arguments de commande
Description:
	Extrait le nombre de processus a creer de la ligne de
	commande. Si une erreur a lieu, le processus termine.
	Appel creerEnfantEtLire pour creer un enfant, et lire
	les donnees de l'enfant.
-------------------------------------------------------------*/

int main(int ac, char **av)
{
	int numeroProcessus;

	if (ac == 2)
	{
		if (sscanf(av[1], "%d", &numeroProcessus) == 1)
		{
			creerEnfantEtLire(numeroProcessus);
		}
		else
			fprintf(stderr, "Ne peut pas traduire argument\n");
	}
	else
		fprintf(stderr, "Arguments pas valide\n");
	return (0);
}

/*-------------------------------------------------------------
Function: creerEnfantEtLire
Arguments:
	int prcNum - le numero de processus
Description:
	Cree l'enfant, en y passant prcNum-1. Utilise prcNum
	comme identificateur de ce processus. Aussi, lit les
	messages du bout de lecture du tuyau et l'envoie a
	la sortie standard (df 1). Lorsqu'aucune donnee peut
	etre lue du tuyau, termine.
-------------------------------------------------------------*/

void creerEnfantEtLire(int prcNum)
{

	char msgRead[BUFFER_SIZE]; // messages lu par le bout de lecture du tuyau
	int fd[2];
	pid_t pid;
	char buffer[BUFFER_SIZE]; // Buffer qui va contenir prcNum en tant que char[]
	snprintf(buffer, sizeof(buffer), "%d", prcNum - 1);
	char *args[] = {"./cpr", buffer, NULL};

	if (prcNum == 1) // ne cree pas d'enfant
	{
		printf("Processus %d commence\n", prcNum);
		fflush(stdout);
		sleep(5);
		printf("Processus %d termine\n", prcNum);
		fflush(stdout);
	}
	else // cree des enfants
	{
		if (pipe(fd) < 0) // cree un tuyaux avec deux descripteur 0 lecture 1 ecriture
		{
			fprintf(stderr, "Pipe failed");
			exit(1);
		}
		pid = fork();
		if (pid == -1) // erreure
		{
			fprintf(stderr, "Fork failed");
			exit(1);
		}
		else if (pid == 0) // enfant
		{
			close(fd[READ_END]);
			dup2(fd[WRITE_END], STDOUT_FILENO); // attacher le bout écrivant du tuyau à la sortie standard de l’enfant
			close(fd[WRITE_END]);
			execvp(args[0], args); // cree un enfant en executant cpr num-1
			fprintf(stderr, "Execvp failed");
			exit(1);
		}
		else // parent
		{
			printf("Processus %d commence\n", prcNum);
			fflush(stdout);
			close(fd[WRITE_END]);
			ssize_t nbytes;
			while ((nbytes = read(fd[READ_END], msgRead, BUFFER_SIZE)) > 0)
			{
				write(STDOUT_FILENO, msgRead, nbytes); // ecrit au terminal la sortie du bout de lecture
			}
			printf("Processus %d termine\n", prcNum);
			fflush(stdout);
			close(fd[READ_END]);
			sleep(10);
			exit(0);
		}
	}
}
