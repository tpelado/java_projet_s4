import java.util.Scanner;

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
		int resultat = 0;
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
				break;
			case 2:
				System.out.println("Vous avez choisi d'afficher la blockchain...");
				break;
			case 3:
				System.out.println("Vous avez choisi d'afficher un block de la blockchain...");
				break;
			case 0:
				System.out.println("Goodbye. May we meet again...");
				quitter = true;
			}
		}
	}
}
