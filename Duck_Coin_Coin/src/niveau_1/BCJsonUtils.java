package niveau_1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import com.google.gson.stream.JsonWriter;

public class BCJsonUtils {

    public static Blockchain BCJsonReader(String filename) {

        Gson gson = new Gson();

        try (Reader reader = new FileReader(filename)) {

	// Convert JSON to Java Object
            Blockchain bc = gson.fromJson(reader, Blockchain.class);
            bc.afficherBlockchain();
            reader.close();
            return bc;

			// Convert JSON to JsonElement, and later to String
            /*JsonElement json = gson.fromJson(reader, JsonElement.class);
            String jsonInString = gson.toJson(json);
            System.out.println(jsonInString);*/

        } catch (IOException e) {
        	System.err.println("le fichier "+filename+" n'existe pas. vérifiez le nom et réessayez");
          //  e.printStackTrace();
        }
        return null;
    }
    /*
    public static void BlockchainToJson(Blockchain blk, String fichier)
    {
    	int i=0;
    	int j=0;
    	FileOutputStream fop = null;
    	File file;
    	try
    	{
    		file = new File("./"+fichier); // on créé le fichier dans le rep courant
    		fop = new FileOutputStream(file); // on ouvre "le description de fichier", un peu comme en C
    		OutputStreamWriter out = new OutputStreamWriter(fop); 
    		JsonWriter writer = new JsonWriter(out);// on créé l'objet qui va nous permettre d'écrire dans le fichier texte, et on le redirige vers le flux créé juste au dessus
    		if(!file.exists())
    		{
    			file.createNewFile();
    		}
    		writer.setIndent("\t"); // règle apparemment l'indentation
    		writer.beginObject();
    		writer.name("difficulte").value(blk.getDifficulty());
    		writer.name("nbr_blocks").value(blk.getNbrBlocks());
    		writer.name("liste_block").beginArray();
    		for(i=blk.getNbrBlocks()-1;i>=0;i--)
    		{
    			writer.beginObject();
    			writer.name("index").value(blk.getBlock(i).getIndex());
    			writer.name("hash_precedent").value(blk.getBlock(i).getHash_precedent());
    			writer.name("timestamp").value(blk.getBlock(i).getStringTimestamp());
    			writer.name("nbr_transactions").value(blk.getBlock(i).getNbTransaction());
    			writer.name("liste_transaction").beginArray();
    			for(j=0;j<blk.getBlock(i).getNbTransaction();j++)
    			{
    				writer.value(blk.getBlock(i).getTransactionTab()[j].getStringTransaction());
    			}
    			writer.endArray();
    			writer.name("merkleRoot").value(blk.getBlock(i).getmerkleRoot());
    			writer.name("blockHash").value(blk.getBlock(i).getblockHash());
    			writer.name("nonce").value(blk.getBlock(i).getNonce());
    			writer.endObject();
    		}
    		writer.endArray();
    		writer.endObject();
    		writer.flush();
    		writer.close();
    		//fop.close();
    		System.out.println("fini");
    	} catch(IOException e){
    		e.printStackTrace();
    	} finally {
			try {
				if(fop != null)
				{
					fop.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
    }*/
    
    public static void BCJsonWriter(Blockchain BlockC, String filename){
    	/* modifié parce que sinon ça plantait tout*/ 
        // JSON Parser
        //1. Convert object to JSON string
    	File file;
    	Gson gson = new GsonBuilder().setPrettyPrinting().create(); // créé un "builder" json qui rajoute les indents correctement
        String json = gson.toJson(BlockC); // converti la blockchain en String
        //System.out.println(json);
        file = new File("./"+filename); // on créé le fichier dans le répertoire courant
		FileOutputStream fop = null; 
		try
		{
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e)
		{
			System.err.println("le fichier "+filename+" n'existe pas. vérifiez le nom et réessayez");
		} // on ouvre "le description de fichier", un peu comme en C
		OutputStreamWriter out = new OutputStreamWriter(fop);
		try
		{
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

    }

}
