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

#define MAX_NUM_PAGE 9
#define LONGUEUR_MIN_CHAINE_REFERENCE 8
#define LONGUEUR_MAX_CHAINE_REFERENCE 20

/* Prototype */
void generer_chaine_reference(int *, int);

int main()
{
    int longueur_chaine_reference = LONGUEUR_MIN_CHAINE_REFERENCE + rand() % (LONGUEUR_MAX_CHAINE_REFERENCE - LONGUEUR_MIN_CHAINE_REFERENCE + 1);
    int chaine_reference[longueur_chaine_reference];
    generer_chaine_reference(chaine_reference, longueur_chaine_reference);

    printf("Chaîne de référence : ");
    for (int i = 0; i < longueur_chaine_reference; i++)
    {
        printf("%d ", chaine_reference[i]);
    }
    printf("\n");

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
