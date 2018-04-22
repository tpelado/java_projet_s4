package niveau_1;
import java.util.Scanner;
import niveau_1.Blockchain;
public class Main
{
	static private void affiche_menu()
	{
		System.out.println("1 : Générer une blockchain");
		System.out.println("2 : Afficher la blockchain");
		System.out.println("3 : Afficher un bloc de la blockchain");
		System.out.println("4 : Exporter la blockchain en fichier JSON"); // et la toison d'or
		System.out.println("5 : Importer la blockchain depuis un fichier JSON"); // et la toison d'or... hahaahahaahah
		System.out.println("0 : Quitter");
	}

	static public void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		String tempo;
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
				if(blockchain.isEmpty())
				{
					blockchain.genererBlockchain();
				}
				else
				{
					System.out.println("la blockchain existe déjà. voulez vous la regénérer ? (yes/no)");
					tempo = scan.next();
					System.out.println(tempo);
					if(tempo.equals("yes"))
					{
						blockchain.genererBlockchain();
					}
				}
				//blockchain.afficher_blockchain();
				affiche_menu();
				break;
			case 2:
				System.out.println("Vous avez choisi d'afficher la blockchain...");
				if(!blockchain.isEmpty())
					blockchain.afficherBlockchain();
				else
					System.out.println("\n\n!!! La blockchain n'existe pas !!! \n!!! Il faut la générer d'abord !!!\n\n");
				affiche_menu();
				break;
			case 3:
				System.out.println("Vous avez choisi d'afficher un block de la blockchain...");
				
				if(!blockchain.isEmpty())
				{
					System.out.println("Rentrez l'index du block (on compte à partir de 0)");
					int nbr_block = scan.nextInt();
					if(nbr_block<blockchain.getNbrBlocks())
						blockchain.afficherBlockBlockchain(nbr_block);
					else
						System.out.println("ce block n'existe pas (encore)");
				}
				else
					System.out.println("\n\n!!! La blockchain n'existe pas !!! \n!!! Il faut la générer d'abord !!!\n\n");
				affiche_menu();
				break;
			case 4:
				System.out.println("Vous avez choisi d'exporter la blockchain");
				if(!blockchain.isEmpty())
				{
					System.out.println("rentrer le nom de votre fichier (sera créé dans le dossier du projet)");
					tempo = scan.next();
					if(!tempo.contains(".")) { // rajoute l'extension si l'utilisateur l'oublie
						tempo = tempo.concat(".json");
					}
					niveau_1.BCJsonUtils.BCJsonWriter(blockchain, "./"+tempo);
				}
				else
					System.out.println("\n\n!!! La blockchain n'existe pas !!! \n!!! Il faut la générer d'abord !!!\n\n");
				affiche_menu();
				break;
			case 5:
				System.out.println("Vous avez choisi d'importer la blockchain");
				if(!blockchain.isEmpty())
				{
					System.out.println("la blockchain existe déjà. voulez vous l'écraser ? (yes/no)");
					tempo = scan.next();
					System.out.println(tempo);
					if(tempo.equals("yes"))
					{
						System.out.println("rentrer le nom de votre fichier (doit être dans le dossier du projet)");
						tempo = scan.next();
						blockchain = niveau_1.BCJsonUtils.BCJsonReader("./"+tempo);
						blockchain.afficherBlockchain();
					}
					
				}
				else
					System.out.println("rentrer le nom de votre fichier (doit être dans le dossier du projet)");
					tempo = scan.next();
					blockchain = niveau_1.BCJsonUtils.BCJsonReader("./"+tempo);
					if(blockchain != null)
						blockchain.afficherBlockchain();
					else
						System.err.println("erreur import blokchain");
				affiche_menu();
				break;
			case 0:
				System.out.println("Bye");
				try
				{
					Thread.sleep(2000);
				} catch (InterruptedException e)
				{
					// on s'en fout un peu vu qu'on va jamais interrompre ça 
				}
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				quitter = true;
			}
		}
		scan.close();
	}
}
