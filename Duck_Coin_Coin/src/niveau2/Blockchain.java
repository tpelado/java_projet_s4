package niveau2;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Tanguy
 *
 */
public class Blockchain
{
	private int difficulte; // stocke l'attribut de difficult� (utilis� pour le minage plus tard)
	private int nbrBlocks; // le nombre de blocks de la blockchain.
	private Block[] liste_block; // la liste des blocks

	/**
	 * initialisation de la blockchain
	 * 
	 */
	public Blockchain()
	{
		this.difficulte = -1;
		this.nbrBlocks = -1;
		this.liste_block = null;
	}

	/**
	 * si on veut importer une blockchain par dessus une d�j� existante, ou simplement en recr�er une
	 */
	public void viderBlockchain()
	{
		this.difficulte = -1;
		this.nbrBlocks = -1;
		this.liste_block = null;
	}

	/**
	 * affiche la blockchain
	 */
	public void afficherBlockchain()
	{
		int i = 0;
		System.out.println("------------DEBUT BLOCKCHAIN--------------");
		System.out.println("longeur blockchain : " + this.nbrBlocks + " blocks ");
		System.out.println("difficult� blockchain : " + this.difficulte);
		for (; i < this.nbrBlocks; i++)
		{
			System.out.println("-------BLOCK " + (i + 1) + " ----------"); // compte les blocks � partir de un parce que
																			// c'est plus naturel
			this.liste_block[i].afficherBlock();
		}
		System.out.println("------------FIN BLOCKCHAIN--------------");

	}

	/**
	 * teste si la blockchain est vide (non g�n�r�e ou �ronn�e)
	 * 
	 * @return true si la blockchain est vide, False sinon
	 */
	public boolean isEmpty()
	{
		return this.nbrBlocks == -1;
	}

	public void genererBlockchain() // demande � l'utulisateur les attributs de la blockchain qu'il veut g�n�rer
	{
		@SuppressWarnings("resource") // je sais que cette erreur existe, et je ne peux pas trop la corriger. donc je
										// supprime l'avertissement dans eclipse
		Scanner scan = new Scanner(System.in); // fermer le scanner correspond � fermer system.in, et j'ai pas trop
												// envie
		/*
		 * faudrait passer soit: - le scanner en param�tre - les attributs de la blockchain...
		 */
		int diff = -1;
		int nbrBlocks = -1;
		System.out.println("Rentrez la difficult� de minage de votre blockchain (nombre entier)");
		while (diff < 0) // r�cup�re la difficult� voulue par l'utilisateur
		{
			try
			{
				diff = scan.nextInt();
				if(diff<0)
				{
					System.out.println("UN ENTIER POSITIF OU NUL");
					scan.nextLine();
				}
			} catch (InputMismatchException err) // si jamais l'utilisateur rentre des lettres...
			{
				System.out.println("UN ENTIER");
				scan.nextLine();
			}
			
		}

		while (nbrBlocks < 1) // r�cup�re le nombre de block voulu par l'utilisateur
		{
			System.out.println("Rentrez le nombre de blocks de votre blockchain (nombre entier sup�rieur � 0)");
			try
			{
				nbrBlocks = scan.nextInt();
			} catch (InputMismatchException err)
			{
				System.out.println("UN ENTIER");
				scan.nextLine();
			}
			if(nbrBlocks<1)
			{
				System.out.println("UN ENTIER POSITIF");
				scan.nextLine();
			}
		}
		this.difficulte = diff;
		this.nbrBlocks = nbrBlocks;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // on s'en sert pour savoir combien de temps on met pour g�n�rer la blockchain
		this.liste_block = niveau2.Block.genererListeBlock(this.difficulte, this.nbrBlocks); // g�n�re une liste de block al�atoire avec un block genesis au d�but
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		long temps = timestamp2.getTime() - timestamp.getTime(); // calcule la dur�e en millisecondes pour g�n�rer la blockchain
		if(!this.isValid()) // d�termine la validit� de la blockchain
		{
			System.err.println("erreur de g�n�ration blockchain...");
		}
		System.out.print(temps / 1000); // transforme "temps" en secondes
		System.out.println(" secondes pour g�n�rer la blockchain");
	}

	/**
	 * fonction v�rifiant la validit� de la blockchain
	 * 
	 * @return une valeur bool�enne indiquant la validit� de la blockchain
	 */
	public boolean isValid()
	{
		int i = 1;
		boolean retour = true;
		// on v�rifie d'abord si le bloc genesis est bien le genesis (nonce � 0, hash pr�c�dent � 0, et 1 seule transaction)
		Block tempo = new Block(); // initialise un block vide , qui servira de tampon pendant les v�rifications, histoire de ne pas modifier accidentellement des valeurs de la blockchain
		tempo.copyBlockFrom(this.getGenesis()); // copie le block genesis
		if(tempo.getNonce() == 0 && tempo.getHash_precedent() == "0" && (tempo.getTransactionTab())[0].getVerifGenesis().equals("GenesisGenesisGenesis"))
		{
			retour = true;
		} else
		{
			System.err.println("erreur genesis");
			retour = false;
		}
		for (; i < this.nbrBlocks; i++)
		/** pour tout les blocks on va v�rifier si le hash est correct,la merkle root, et si ils sont chain�s correctement **/
		{
			if(this.getBlock(i).getHash_precedent() != this.getBlock(i - 1).getblockHash()) // hash pr�c�dent
			{
				// si jamais le hash pr�c�dent du block actuel est diff�rent du hash du block pr�c�dent
				retour = false;
			}
			tempo.copyBlockFrom(this.getBlock(i));
			tempo.setNonce(0);
			/**
			 * on aurait aussi pu mettre this.getnonce() - 1 dans tout les cas il fallait une valeur inf�rieure � la nonce du block vu que c'est le nombre d'it�rations n�cessaire pour obtenir un hash satisfaisant la difficult�. en partant avec une nonce �gale � celle du block, on aurait du trouver le prochain hash satisfaisant la difficult�
			 */
			tempo.calculerHashBlock(this.getDifficulty());
			if(!(tempo.getblockHash().equals(this.getBlock(i).getblockHash()))) // si le hash calcul� n'est pas le m�me, il ya un probl�me, et la blockchain n'est pas valide
			{
				System.err.println("ERREUR HASH BLOCK");
				System.out.println(tempo.getblockHash());
				System.out.println(this.getBlock(i).getblockHash());
				retour = false;
			}
			tempo.calculerMerkelBlock();
			if(!(tempo.getmerkleRoot().equals(this.getBlock(i).getmerkleRoot())))
			{
				System.err.println("ERREUR HASH MERKLE");
				System.out.println(tempo.getmerkleRoot());
				System.out.println(this.getBlock(i).getmerkleRoot());
				retour = false;
			}
		}

		return retour;
	}

	/** ------ getters and setters ----- */
	/**
	 * renvoie le block genesis, ou null si la blockchain est vide
	 * 
	 * @return la genesis ou null si elle n'existe pas
	 */
	private Block getGenesis()
	{
		try
		{
			return this.liste_block[0];
		} catch (NullPointerException e)
		{
			System.err.println("erreur Genesis inexistante.");
			return null; // du coup, on renvoie null si le block genesis n'existe pas
		}
	}

	/**
	 * retourne le block i de la lste, mais en plus joli qu'un acc�s au tableau
	 * 
	 * @param i
	 *            : index du block demand�
	 * @return le block d'index i
	 **/
	public Block getBlock(int i)
	{
		try
		{
			return this.liste_block[i];
		} catch (NullPointerException e)
		{
			System.err.println("erreur block inexistante.");
			return null; // du coup, on renvoie null si le block demand� n'existe pas
		}
	}

	/**
	 * getter nombre blocks
	 * 
	 * @return le nombre de blocks de la blockchain
	 */
	public int getNbrBlocks()
	{
		return this.nbrBlocks;
	}

	/**
	 * getter difficult�
	 * 
	 * @return la difficult� de la blockchain
	 */
	public int getDifficulty()
	{
		return this.difficulte;
	}

	/**
	 * affiche un block de la blockchain, ici aussi en plus joli
	 * 
	 * @param nbrBlock
	 *            : index du block � afficher
	 */
	public void afficherBlockBlockchain(int nbrBlock)
	{
		this.getBlock(nbrBlock).afficherBlock();

	}

}
