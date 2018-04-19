package niveau_1;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Blockchain
{
	private int difficulte;
	private int nbr_blocks;
	private Block[] liste_block;
	private Scanner scan;
	
	/**
	 * @param difficulte
	 * @param nbr_blocks
	 * @param liste_block
	 */
	public Blockchain()
	{
		this.difficulte = -1;
		this.nbr_blocks = -1;
		this.liste_block = null;
	}
	 
	
	public void afficher_blockchain()
	{
		int i=0;
		System.out.println("------------DEBUT BLOCKCHAIN--------------");
		System.out.println("longeur blockchain : "+this.nbr_blocks+" blocks ");
		System.out.println("difficulté blockchain : "+this.difficulte);
		for(;i<this.nbr_blocks;i++)
		{
			System.out.println("-------BLOCK "+(i+1)+" ----------");
			this.liste_block[i].afficherBlock();
		}
		System.out.println("------------FIN BLOCKCHAIN--------------");
		
	}
	
	public boolean is_empty()
	{
		return this.nbr_blocks==-1;
	}
	public void generer_blockchain() // demande à l'user les attributs de la blockchain qu'il veut générer
	{
		int i=0;
		scan = new Scanner(System.in);
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
		
		while(nbr_blocks<1)
		{
			System.out.println("Rentrez le nombre de blocks de votre blockchain (nombre entier supérieur à 0)");
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
		this.difficulte = diff;
		this.nbr_blocks = nbr_blocks;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Block[] list_block = new Block[nbr_blocks];
		list_block[0] = new Block(0); // passer un chiffre en paramètre active la génération genesis
		list_block[0].calculerHashBlock(difficulte);
		for(i=1;i<nbr_blocks;i++)
		{
			list_block[i] = new Block();
			// obligé de calculer le hash ici , on peut pas choper la diff dans la classe block
			list_block[i].calculerHashBlock(this.difficulte);
			list_block[i].setHash_precedent(list_block[i-1].getHash_block());
		}
		this.liste_block = list_block;
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		long test = timestamp2.getTime()-timestamp.getTime();
		System.out.print(test/1000);
		System.out.println(" secondes pour générer la blockchain");
	}

	public int getNbrBlocks()
	{
		return this.nbr_blocks;
	}
	
	public int getDifficulty()
	{
		return this.difficulte;
	}
	public void afficher_block_blockchain(int nbr_block)
	{
		this.liste_block[nbr_block].afficherBlock();
		
	}


}


