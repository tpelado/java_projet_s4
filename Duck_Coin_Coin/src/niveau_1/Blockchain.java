package niveau_1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Blockchain
{
	private int difficulte;
	private int nbr_blocks;
	private Block[] liste_block;
	
	/**
	 * @param difficulte
	 * @param nbr_blocks
	 * @param liste_block
	 */
	public Blockchain(int difficulte, int nbr_blocks)
	{
		Block[] liste = new Block[nbr_blocks];
		this.difficulte = difficulte;
		this.nbr_blocks = nbr_blocks;
		this.liste_block = liste;
	}
	 
	
	public void afficher_blockchain(Blockchain blck)
	{
		System.out.println("longeur blockchain : "+blck.nbr_blocks+" blocks ");
		
	}
	public Blockchain generer_blockchain() // demande à l'user les attributs de la blockchain qu'il veut générer
	{
		Scanner scan = new Scanner(System.in);
		int diff=-1;
		int nbr_blocks=-1;
		
		System.out.println("Rentrez la difficulté de minage de votre blockchain (nombre entier)");
		while(diff<0)
		{
			try 
			{
				diff = scan.nextInt();
			}
			catch (InputMismatchException err)
			{
				System.out.println("UN ENTIER");
				scan.nextLine();
			}
		}
		
		System.out.println("Rentrez le nombre de blocks de votre blockchain (nombre entier)");
		while(nbr_blocks<0)
		{
			try 
			{
				nbr_blocks = scan.nextInt();
			}
			catch (InputMismatchException err)
			{
				System.out.println("UN ENTIER");
				scan.nextLine();
			}
		}
		Blockchain blck = new Blockchain(difficulte, nbr_blocks);
		return blck;
	}


}


