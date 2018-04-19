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
		private String hash_merkel;
		private String hash_block;
		private int nonce;
		private Transaction liste_transaction[];
		/**
		 * @param index
		 * @param hash_precedent
		 * @param nbr_transaction
		 * @param liste_transaction
		 * @param hash_merkel
		 * @param nonce
		 */
	/*	public Block(int index, String hash_precedent, int nbr_transaction, Transaction[] liste_transaction,
				String hash_merkel, int nonce)
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.index = index;
			this.timestamp = timestamp;
			this.hash_precedent = hash_precedent;
			this.nbr_transaction = nbr_transaction;
			this.liste_transaction = liste_transaction;
			this.hash_merkel = hash_merkel;
			this.nonce = nonce;
		}
		*/
		public Block(int index)
		{
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
			this.hash_block = ""/*TODO add hash */;
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
			this.nonce = 0; /* TODO add nonce */ 
			this.hash_block = "";/*TODO add hash */
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
			System.out.println("hash block = "+this.hash_block);
			System.out.println("hash merkel = "+this.hash_merkel);
			
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
			this.hash_merkel=tab[0];
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
			do
			{
				tempo = niveau_1.HashUtil.applySha256(this.hash_precedent+this.getStringTimestamp()+this.hash_merkel+this.nonce);
				this.nonce++;
			}while(!minage(tempo,difficulte));
			this.hash_block=tempo;
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
		 * @return the hash_merkel
		 */
		public String getHash_merkel()
		{
			return hash_merkel;
		}

		/**
		 * @param hash_merkel the hash_merkel to set
		 */
		public void setHash_merkel(String hash_merkel)
		{
			this.hash_merkel = hash_merkel;
		}

		/**
		 * @return the hash_block
		 */
		public String getHash_block()
		{
			return hash_block;
		}

		/**
		 * @param hash_block the hash_block to set
		 */
		public void setHash_block(String hash_block)
		{
			this.hash_block = hash_block;
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
}
