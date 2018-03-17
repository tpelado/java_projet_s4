package niveau_1;
import java.sql.Timestamp;
public class Block
{
		private int index;
		private String timestamp;
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
		public Block(int index, String hash_precedent, int nbr_transaction, Transaction[] liste_transaction,
				String hash_merkel, int nonce)
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.index = index;
			this.timestamp = timestamp.toString();
			this.hash_precedent = hash_precedent;
			this.nbr_transaction = nbr_transaction;
			this.liste_transaction = liste_transaction;
			this.hash_merkel = hash_merkel;
			this.nonce = nonce;
		}
		
		public Block generer_genesis()
		{
			Transaction[] tab_trans = new Transaction[0];
			Block genesis = new Block(0, "0",0, tab_trans,"hash merkel",0);
			return genesis;
		}
		
		public void afficherBlock(Block blk)
		{
			int i=0;
			System.out.println("index = "+blk.index);
			System.out.println("timestamp = "+blk.index);
			System.out.println("hash_précédent = "+blk.index);
			System.out.println("nombre de transactions = "+blk.index);
			System.out.println("transactions = ");
			for(i=0;i<blk.nbr_transaction;i++)
			{
				
			}
			System.out.println("index = "+blk.index);
			
		}
		
}
