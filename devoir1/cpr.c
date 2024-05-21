/*------------------------------------------------------------
Fichier: cpr.c

Nom: Chloe Al-Frenn
Numero d'etudiant:  300211508

Description: Ce programme contient le code pour la creation
             d'un processus enfant et y attacher un tuyau.
	     L'enfant envoyera des messages par le tuyau
	     qui seront ensuite envoyes a la sortie standard.

Explication du processus zombie
(point 5 de "A completer" dans le devoir):

	(s.v.p. completez cette partie);

-------------------------------------------------------------*/
#include <stdio.h>
#include <sys/select.h>
#include <unistd.h>	

#define BUFFER_SIZE 50
#define READ_END 0
#define WRITE_END 1

/* Prototype */
void creerEnfantEtLire(int );

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

    if(ac == 2)
    {
       if(sscanf(av[1],"%d",&numeroProcessus) == 1)
       {
           creerEnfantEtLire(numeroProcessus);
       }
       else fprintf(stderr,"Ne peut pas traduire argument\n");
    }
    else fprintf(stderr,"Arguments pas valide\n");
    return(0);
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
	char write_msg[BUFFER_SIZE];
	char read_msg[BUFFER_SIZE];
	int fd[2];
	pid_t pid;
	char buffer[BUFFER_SIZE]; // Buffer qui va contenir prcNum en tant que char[]
	snprintf(buffer, sizeof(prcNum), "%d", prcNum-1);
    char* args[] = {"cpr.c", buffer, NULL};
	


	if(prcNum == 1){ //ne cree pas d'enfant
		printf("Processus %d commence\n", prcNum);
		sleep(5);
		printf("Processus %d termine\n", prcNum);
	} else { //cree des enfants
		printf("Processus %d commence\n", prcNum);
		if(pipe(fd) == -1) //cree un tuyaux avec deux descripteur 0 lecture 1 ecriture
		{
			fprintf(stderr ,"Pipe failed");
			exit(1);
		}
		pid = fork();
		if(pid == -1) //erreure
		{
			fprintf(stderr, "Fork failed");
			exit(1);
		}
		else if(pid == 0) //child
		{
			dup2(fd[WRITE_END], STDOUT_FILENO); //attacher le bout écrivant du tuyau à la sortie standard de l’enfant
			close(fd[READ_END]);
			close(fd[WRITE_END]); //Close both ends of the pipe
        	execvp(args[0], args); //cree un enfant en executant cpr num-1
			//should exit be added?
		} else { //parent
			read(fd[READ_END], read_msg, BUFFER_SIZE); //lire du bout de lecture du tuyau
			write(fd[WRITE_END], write_msg, strlen(write_msg)+1);
			//do i close both ends of the pipe here or after the wait?
			wait(); //is the wait necessary
			printf("Processus %d termine\n", prcNum);
			//should exit be added?
		}
	}
    

		//Quand un processus crée un enfant, il doit d’abords créer un tuyau et (fait) 
		//attacher le bout écrivant du tuyau à la sortie standard de l’enfant avant d’exécuter (fait)
		//la commande « cpr num-1 ». ecriture side (sortie standar is 1 ) (fait)

		//Tous les processus qui créent des enfants (i.e. processus 2 à n) 
		//lisent du bout de lecture du tuyau et (read?) (fait)
		//écrivent toutes données lues à leur sortie standard. (write?) or print? or sprintf() 
		//n’attachez pas les bouts de lecture des tuyaux aux entrées standards 
	
	
	/* S.V.P. completez cette fonction selon les
       instructions du devoirs. */
}
