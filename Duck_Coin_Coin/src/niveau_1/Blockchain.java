package niveau_1;

import java.sql.Timestamp;
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
	public Blockchain()
	{
		this.difficulte = -1;
		this.nbr_blocks = -1;
		this.liste_block = null;
	}
	 
	public void viderBlockchain()
	{
		this.difficulte = -1;
		this.nbr_blocks = -1;
		this.liste_block = null;
	}
	public void afficherBlockchain()
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
	
	public boolean isEmpty()
	{
		return this.nbr_blocks==-1;
	}
	public void genererBlockchain() // demande à l'user les attributs de la blockchain qu'il veut générer
	{
		int i=0;
		Scanner scan = new Scanner(System.in); // fermer le scanner correspond à fermer system.in, et j'ai pas trop envie
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
		list_block[0].calculerHashBlock(0);
		for(i=1;i<nbr_blocks;i++)
		{
			list_block[i] = new Block();
			list_block[i].setIndex(i);
			// obligé de calculer le hash ici , on peut pas choper la diff dans la classe block
			list_block[i].setHash_precedent(list_block[i-1].getblockHash());
			list_block[i].calculerHashBlock(this.difficulte);
			
		}
		this.liste_block = list_block;
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		long test = timestamp2.getTime()-timestamp.getTime();
		if(!this.isValid())
		{
			System.err.println("erreur de génération blockchain...");
		}
		System.out.print(test/1000);
		System.out.println(" secondes pour générer la blockchain");
	}
	public boolean isValid()
	{
		int i=1;
		boolean retour = true;
		// on verifie d'abord si le bloc genesis est bien le genesis (nonce à 0, hash prec à 0, et 1 seule trans
		Block tempo=new Block();
		tempo.copyBlockFrom(this.getGenesis());
		if(tempo == null) 
		{
			retour = false;
		}
		else 
		{
			if(tempo.getNonce()==0 && tempo.getHash_precedent()=="0" && (tempo.getTransactionTab())[0].getTransaction()=="Genesis")
			{
				retour = true;
			}
			else
			{
				retour = false;
			}
		}
		for(;i<this.nbr_blocks;i++)
		{
			if(this.getBlock(i).getHash_precedent()!=this.getBlock(i-1).getblockHash())
			{
				// si jamais le hash précédent du block actuel est différent du hash du block précédent
				retour = false;
			}
			tempo.copyBlockFrom(this.getBlock(i));
			tempo.setNonce(0);
			tempo.calculerHashBlock(this.getDifficulty());
			if(!(tempo.getblockHash().equals(this.getBlock(i).getblockHash())))
			{
				System.out.println(tempo.getblockHash());
				System.out.println(this.getBlock(i).getblockHash());
				retour = false;
			}
		tempo.calculerMerkelBlock();
		if(!(tempo.getmerkleRoot().equals(this.getBlock(i).getmerkleRoot())))
		{
			System.out.println(tempo.getmerkleRoot());
			System.out.println(this.getBlock(i).getmerkleRoot());
			retour = false;
		}
		}
		
		return retour;
	}
	
	private Block getGenesis()
	{
		try {return this.liste_block[0];}
		catch(NullPointerException e)
		{
			System.err.println("erreur Genesis inexistante.");
			return null; // du coup, on renvoie null si le block genesis n'existe pas
		}
	}
	/* retourne le block i de la lste, mais en plus joli*/
	public Block getBlock(int i)
	{
		try {return this.liste_block[i];}
		catch(NullPointerException e)
		{
			System.err.println("erreur block inexistante.");
			return null; // du coup, on renvoie null si le block demandé n'existe pas
		}
	}
	
	public int getNbrBlocks()
	{
		return this.nbr_blocks;
	}
	
	public int getDifficulty()
	{
		return this.difficulte;
	}
	public void afficherBlockBlockchain(int nbr_block)
	{
		this.liste_block[nbr_block].afficherBlock();
		
	}


}


