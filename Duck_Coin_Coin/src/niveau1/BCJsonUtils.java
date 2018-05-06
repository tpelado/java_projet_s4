package niveau1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/***
 * au départ donné sur moodle, modifiée pour que :
 * - ça fonctionne
 * - le fichier json sorti ne soit pas une bouillie immonde impossible à lire 
 */
public class BCJsonUtils {
	/**
	 * lit et importe une blockchain depuis un fichier (texte, json...)
	 * @param filename nom du fichier a lire 
	 * @return la blockchain lue, ou null en cas d'erreur
	 */
    public static niveau1.Blockchain BCJsonReader(String filename) {
        Gson gson = new Gson(); // objet gson générique pour lire et écrire des fichiers JSON
        try (Reader reader = new FileReader(filename)) {
        	// parse le fichier et rempli un objet de classe blockchain par association entre les attributs de l'objet et ceux présents dans le fichier
        	/* si jamais le fichier json comporte des noms de valeurs différentes de celles présentes dans la classe, cela ne FONCTIONNERA PAS */ 
            niveau1.Blockchain bc = gson.fromJson(reader, niveau1.Blockchain.class);
            reader.close(); // on n'oublie pas de fermer le reader à la fin
            return bc; // et on retourne la blockchain
        } catch (IOException e) { // catcher d'exception si jamais le fichier n'existe pas ou n'es pas accessible en lecture
        	System.err.println("le fichier "+filename+" n'existe pas. vérifiez le nom et réessayez");
        }
        return null; // retourne null en cas d'erreur
    }
    
    public static niveau1.Blockchain BCJsonReaderFromString(String jsonString) {
        Gson gson = new Gson(); // objet gson générique pour lire et écrire des fichiers JSON
            niveau1.Blockchain bc = gson.fromJson(jsonString, niveau1.Blockchain.class);
            return bc; // et on retourne la blockchain
    }
    
    public static String BCJsonWriterToString(niveau1.Blockchain blockchain)
	{
		// JSON Parser
		// 1. Convert object to JSON string
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("EEE dd, yyyy HH:mm:ss a").create(); // créé un "builder" json qui rajoute les indents correctement (pretty print)
		String json = gson.toJson(blockchain); // converti la blockchain en JSON sous format String
		return json;
	}
    
    
    public static void BCJsonWriterFromString(String jsonBlockchain, String filename){
        // JSON Parser
        //1. Convert object to JSON string
        File file = new File(filename); // on créé le fichier dans le répertoire courant
		FileOutputStream fop = null; // on initialise l'outil pour ecrire dans le fichier
		try
		{
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e)
		{
			System.err.println("le fichier "+filename+" n'existe pas. vérifiez le nom et réessayez");
		} // on ouvre "le descriptor de fichier", en tout cas c'est comme ça que je le comprend 
		OutputStreamWriter out = new OutputStreamWriter(fop);
		try 
		{ // on ecrit le string généré dans le fichier et on le ferme
			out.write(jsonBlockchain);
			out.flush();
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

    }

    public static void BCJsonWriter(niveau1.Blockchain blockchain, String filename){
        // JSON Parser
        //1. Convert object to JSON string
    	File file; // objet fichier
    	Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("EEE dd, yyyy HH:mm:ss a").create(); // créé un "builder" json qui rajoute les indents correctement (pretty print)
        String json = gson.toJson(blockchain); // converti la blockchain en JSON sous format String
        file = new File("./"+filename); // on créé le fichier dans le répertoire courant
		FileOutputStream fop = null; // on initialise l'outil pour ecrire dans le fichier
		try
		{
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e)
		{
			System.err.println("le fichier "+filename+" n'existe pas. vérifiez le nom et réessayez");
		} // on ouvre "le descriptor de fichier", en tout cas c'est comme ça que je le comprend 
		OutputStreamWriter out = new OutputStreamWriter(fop);
		try 
		{ // on ecrit le string généré dans le fichier et on le ferme
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

    }

}
