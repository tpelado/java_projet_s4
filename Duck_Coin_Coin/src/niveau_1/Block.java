 package niveau_1;
 import niveau_1.Blockchain;
import java.sql.Timestamp;
import niveau_1.HashUtil;
public class Block
{
		private int index;
		private Timestamp timestamp;
		private String hash_precedent;
		private int nbr_transaction;
		private String merkleRoot;
		private String blockHash;
		private int nonce;
		private Transaction liste_transaction[];
		/**
		 * @param index
		 * @param hash_precedent
		 * @param nbr_transaction
		 * @param liste_transaction
		 * @param merkleRoot
		 * @param nonce
		 */
	/*	public Block(int index, String hash_precedent, int nbr_transaction, Transaction[] liste_transaction,
				String merkleRoot, int nonce)
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.index = index;
			this.timestamp = timestamp;
			this.hash_precedent = hash_precedent;
			this.nbr_transaction = nbr_transaction;
			this.liste_transaction = liste_transaction;
			this.merkleRoot = merkleRoot;
			this.nonce = nonce;
		}
		*/
		public Block(int index)
		{
		//	System.out.println("genere genesis");
			this.index=0;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.timestamp = timestamp;
			Transaction[] tab_trans = new Transaction[1];
			Transaction trans = new Transaction("Genesis");
			tab_trans[0]=trans;
			this.nbr_transaction = 1;
			this.liste_transaction = tab_trans;
			this.hash_precedent="0";
			this.nonce = 0;
			this.calculerMerkelBlock();
			this.blockHash = "";
		}
		public Block()
		{
			int i=0;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.timestamp = timestamp;
			Transaction[] tab_trans = new Transaction[5];
			for(i=0;i<5;i++)
			{
				tab_trans[i]= new Transaction();
			}
			this.nbr_transaction = 5;
			this.liste_transaction = tab_trans;
			this.hash_precedent="0";
			this.nonce = 0; 
			this.blockHash = "";
			this.calculerMerkelBlock(); /* calcule le hash de merkel */  
		}
		


		public void afficherBlock()
		{
			int i=0;
			System.out.println("index = "+this.index);
			System.out.println("Nonce = "+this.nonce);
			System.out.println("timestamp = "+this.timestamp);
			System.out.println("hash_précédent = "+this.hash_precedent);
			System.out.println("nombre de transactions = "+this.nbr_transaction);
			System.out.println("transactions = ");
			for(i=0;i<this.nbr_transaction;i++)
			{
				System.out.println("transaction : "+this.liste_transaction[i].getStringTransaction());
			}
			System.out.println("hash block = "+this.blockHash);
			System.out.println("merkleRoot = "+this.merkleRoot);
			
		}
		/* tentative pour génerer le hash de merkel en itératif */ 
		public void calculerMerkelBlock()
		{
			int i=0;
			String tempo1,tempo2;
			int nbr_trans = this.nbr_transaction;
			int j=0;
			int n = this.nbr_transaction;
			String[] tab;
			if((this.nbr_transaction)%2==1)
			{
				tab = new String[this.nbr_transaction+1];
				n++;
			}
			else
			{
				tab = new String[this.nbr_transaction];
			}
			for(i=0;i<this.nbr_transaction;i++)
			{
				tab[i] = niveau_1.HashUtil.applySha256(this.liste_transaction[i].getStringTransaction());
			}
			while(nbr_trans>1)
			{
				if((nbr_trans)%2==1)
				{
					tab[nbr_trans] = tab[nbr_trans-1];
					nbr_trans++;
				}
			for(i=0;i<nbr_trans;i+=2)
			{
				tempo1 = tab[i].concat(tab[i+1]);
				tempo1 = niveau_1.HashUtil.applySha256(tempo1);
				tab[j] = tempo1;
				j++;
			}
			nbr_trans/=2;
		}
			this.merkleRoot=tab[0];
	}

		private boolean minage(String hash, int difficulte)
		{
			boolean satisfait = true;
			int i=0;
			for(;i<difficulte;i++)
			{
				if(hash.charAt(i) !='0')
				{
					satisfait=false;
				}
			}
			return satisfait;
		}
		
		
		public void calculerHashBlock(int difficulte)
		{
			String tempo;
			if(difficulte<1)
			{
				tempo = niveau_1.HashUtil.applySha256(this.hash_precedent+this.getStringTimestamp()+this.merkleRoot+this.nonce);
				this.blockHash=tempo;
			}
			else
			{
				do
				{
					tempo = niveau_1.HashUtil.applySha256(this.hash_precedent+this.getStringTimestamp()+this.merkleRoot+this.nonce);
				//	System.out.println("hash obtenu "+tempo+" avec nonce "+this.nonce);
				//	System.out.println("prec : "+this.getHash_precedent()+"\nstamp "+this.getStringTimestamp()+"\nmerkel "+this.merkleRoot);
					this.nonce++;
					// TODO remove this before release
					/*if(this.nonce%100000==0)
					{
						System.out.println("nonce= "+nonce);
					}*/
				}while(!minage(tempo,difficulte));
				this.blockHash=tempo;
			}
		}
		
		public void copyBlockFrom(Block source)
		{
			this.index = source.index;
			this.timestamp = source.timestamp;
			this.nbr_transaction = source.nbr_transaction;
			this.liste_transaction = source.liste_transaction;
			this.hash_precedent=source.hash_precedent;
			this.nonce = source.nonce;
			this.blockHash = "";
			this.calculerMerkelBlock(); 
		}
		
		public void setIndex(int index)
		{
			this.index = index;
		}
		
		/**
		 * @return the timestamp as a string
		 */
		public String getStringTimestamp()
		{
			return timestamp.toString();
		}

		/**
		 * @param timestamp the timestamp to set
		 */
		public void setTimestamp(Timestamp timestamp)
		{
			this.timestamp = timestamp;
		}

		/**
		 * @return the hash_precedent
		 */
		public String getHash_precedent()
		{
			return hash_precedent;
		}

		/**
		 * @param hash_precedent the hash_precedent to set
		 */
		public void setHash_precedent(String hash_precedent)
		{
			this.hash_precedent = hash_precedent;
		}

		/**
		 * @return the merkleRoot
		 */
		public String getmerkleRoot()
		{
			return merkleRoot;
		}

		/**
		 * @param merkleRoot the merkleRoot to set
		 */
		public void setmerkleRoot(String merkleRoot)
		{
			this.merkleRoot = merkleRoot;
		}

		/**
		 * @return the blockHash
		 */
		public String getblockHash()
		{
			return blockHash;
		}

		/**
		 * @param blockHash the blockHash to set
		 */
		public void setblockHash(String blockHash)
		{
			this.blockHash = blockHash;
		}

		/**
		 * @return the nonce
		 */
		public int getNonce()
		{
			return nonce;
		}

		/**
		 * @param nonce the nonce to set
		 */
		public void setNonce(int nonce)
		{
			this.nonce = nonce;
		}
		public Transaction[] getTransactionTab() 
		{
			return this.liste_transaction;
		}
		public int getIndex()
		{
			return this.index;
		}
		public int getNbTransaction()
		{
			return this.nbr_transaction;
		}
}
