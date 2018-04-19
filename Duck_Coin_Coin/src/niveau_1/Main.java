package niveau_1;
import java.util.Scanner;
import niveau_1.Blockchain;
public class Main
{
	private static Scanner scan;

	static private void affiche_menu()
	{
		System.out.println("1 : Générer une blockchain");
		System.out.println("2 : Afficher la blockchain");
		System.out.println("3 : Afficher un bloc de la blockchain");
		System.out.println("0 : Quitter");
	}

	static public void main(String[] args)
	{
		scan = new Scanner(System.in);
		Blockchain blockchain = new Blockchain();
		int resultat = 0;
		String[] tab = new String[3];
		tab[0] = "test";
		boolean quitter = false;
		System.out.println("Bonjour.");
		affiche_menu();
		while (quitter == false)
		{
			resultat = scan.nextInt();
			System.out.println(resultat);
			switch (resultat)
			{
			case 1:
				System.out.println("Vous avez choisi de générer la blockchain...");
				blockchain.generer_blockchain();
				//blockchain.afficher_blockchain();
				affiche_menu();
				break;
			case 2:
				System.out.println("Vous avez choisi d'afficher la blockchain...");
				if(!blockchain.is_empty())
					blockchain.afficher_blockchain();
				else
					System.out.println("\n\n!!! La blockchain n'existe pas !!! \n!!! Il faut la générer d'abord !!!\n\n");
				affiche_menu();
				break;
			case 3:
				System.out.println("Vous avez choisi d'afficher un block de la blockchain...");
				if(!blockchain.is_empty())
				{
					int nbr_block = scan.nextInt();
					if(nbr_block<blockchain.getNbrBlocks())
						blockchain.afficher_block_blockchain(nbr_block);
					else
						System.out.println("ce block n'existe pas (encore)");
				}
				else
					System.out.println("\n\n!!! La blockchain n'existe pas !!! \n!!! Il faut la générer d'abord !!!\n\n");
				affiche_menu();
				break;
			case 0:
				System.out.println("Goodbye. May we meet again...");
				quitter = true;
			}
		}
	}
}
