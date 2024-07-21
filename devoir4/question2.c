/*------------------------------------------------------------
Fichier: question2.c

Nom: Chloe Al-Frenn, Chada Bendriss
Numero d'etudiant:  300211508, 300266679

Description: Ce programme contient le code pour l'implémentation
d'un programme qui implémente les algorithmes de remplacement
de pages FIFO et LRU présentés dans le module 8.

-------------------------------------------------------------*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX_NUM_PAGE 9
#define MIN_CADRE 1
#define MAX_CADRE 7
#define LONGUEUR_MIN_CHAINE_REFERENCE 8
#define LONGUEUR_MAX_CHAINE_REFERENCE 20

/* Prototype */
void generer_chaine_reference(int *, int);
int remplacement_fifo(int *, int, int);

int main()
{
    srand(time(NULL)); // pour que les valeures generees par rand() ne soient pas tjr les memes
    int longueur_chaine_reference = LONGUEUR_MIN_CHAINE_REFERENCE + rand() % (LONGUEUR_MAX_CHAINE_REFERENCE - LONGUEUR_MIN_CHAINE_REFERENCE + 1);
    int chaine_reference[longueur_chaine_reference];
    generer_chaine_reference(chaine_reference, longueur_chaine_reference);

    printf("Chaîne de référence : ");
    for (int i = 0; i < longueur_chaine_reference; i++)
    {
        printf("%d ", chaine_reference[i]);
    }
    printf("\n");

    int nombre_cadres = MIN_CADRE + rand() % (MAX_CADRE - MIN_CADRE + 1);
    int defauts_fifo = remplacement_fifo(chaine_reference, longueur_chaine_reference, nombre_cadres);
    printf("Nombre de défauts de page avec FIFO et %d cadres : %d\n", nombre_cadres, defauts_fifo);

    return 0;
}

/**
 * Génère une chaîne de référence de pages aléatoire.
 * @param chaine_reference - Un tableau d'entiers où la chaîne sera stockée.
 * @param longueur - La longueur de la chaîne de référence.
 */
void generer_chaine_reference(int *chaine_reference, int longueur)
{
    for (int i = 0; i < longueur; i++)
    {
        chaine_reference[i] = rand() % (MAX_NUM_PAGE + 1);
    }
}

/**
 * Implemente l'algorithme de remplacement de pages FIFO.
 * @param chaine_reference - La chaine de référence de pages.
 * @param longueur - La longueur de la chaine de référence.
 * @param nombre_cadres - Le nombre de cadres de pages disponibles.
 * @return defauts - Le nombre de défauts de page.
 */
int remplacement_fifo(int *chaine_reference, int longueur, int nombre_cadres)
{
    int defauts = 0;
    int index = 0;
    int cadres[nombre_cadres];

    for (int i = 0; i < nombre_cadres; i++)
    {
        cadres[i] = -1;
    }

    for (int i = 0; i < longueur; i++)
    {
        int trouver = 0;
        for (int j = 0; j < nombre_cadres; j++)
        {
            if (chaine_reference[i] == cadres[j])
            {
                trouver = 1;
                break;
            }
        }
        if (!trouver)
        {
            cadres[index] = chaine_reference[i];
            index = (index + 1) % nombre_cadres;
            defauts++;
        }

         // Afficher l'état des cadres vous pouvez enlever cette section
        printf("Cadres après accès à la page %d : ", chaine_reference[i]);
        for (int k = 0; k < nombre_cadres; k++) {
            if (cadres[k] != -1) {
                printf("%d ", cadres[k]);
            } else {
                printf("_ ");
            }
        }
        printf("\n");
    }
    return defauts;
}