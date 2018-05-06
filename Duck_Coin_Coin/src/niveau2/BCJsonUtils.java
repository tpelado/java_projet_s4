package niveau2;

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
 * au départ donné sur moodle, modifiée pour que : - ça fonctionne - le fichier json sorti ne soit pas une bouillie immonde impossible à lire
 * 
 */

public class BCJsonUtils
{
	/**
	 * lit et importe une blockchain depuis un fichier (texte, json...)
	 * 
	 * @param filename
	 *            nom du fichier a lire
	 * @return la blockchain lue, ou null en cas d'erreur
	 */
	public static Blockchain BCJsonReader(String filename)
	{
		Gson gson = new Gson(); // objet gson générique pour lire et écrire des fichiers JSON
		try (Reader reader = new FileReader(filename))
		{
			// parse le fichier et rempli un objet de classe blockchain par association entre les attributs de l'objet et ceux présents dans le fichier
			/* si jamais le fichier json comporte des noms de valeurs différentes de celles présentes dans la classe, cela ne FONCTIONNERA PAS */
			Blockchain bc = gson.fromJson(reader, Blockchain.class);
			reader.close(); // on n'oublie pas de fermer le reader à la fin
			return bc; // et on retourne la blockchain
		} catch (IOException e)
		{ // catcher d'exception si jamais le fichier n'existe pas ou n'es pas accessible en lecture
			System.err.println("le fichier " + filename + " n'existe pas. vérifiez le nom et réessayez");
		}
		return null; // retourne null en cas d'erreur
	}

	/**
	 * prend un objet de type blockchain et retourne un String au format JSON de cet objet
	 * 
	 * @param blockchain
	 * @return un string contenant un JSON
	 */
	public static String BCJsonWriterToString(Blockchain BlockC)
	{
		// JSON Parser
		// 1. Convert object to JSON string
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("EEE dd, yyyy HH:mm:ss a").create(); // créé un "builder" json qui rajoute les indents correctement (pretty print)
		String json = gson.toJson(BlockC); // converti la blockchain en JSON sous format String
		return json;
	}

	/**
	 * prend un string contenant une blockchain au format JSON et l'ecrit dans un fichier
	 * 
	 * @param jsonBlockchain
	 *            le string contenant la blockchain
	 * @param filename
	 *            le nom du fichier
	 */
	public static void BCJsonWriterFromString(String jsonBlockchain, String filename)
	{
		// JSON Parser
		// 1. Convert object to JSON string
		File file = new File(filename); // on créé le fichier dans le répertoire courant
		FileOutputStream fop = null; // on initialise l'outil pour ecrire dans le fichier
		try
		{
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e)
		{
			System.err.println("le fichier " + filename + " n'existe pas. vérifiez le nom et réessayez");
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

	/***
	 * @deprecated pas utilisé dans la version graphique de ce projet ecrit l'objet blockchain dans un fichier
	 * @param blockchain
	 *            la blockchain à ecrire
	 * @param filename
	 *            le nom du fichier à créer
	 */
	public static void BCJsonWriter(Blockchain BlockC, String filename)
	{
		// JSON Parser
		// 1. Convert object to JSON string
		File file; // objet fichier
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("EEE dd, yyyy HH:mm:ss a").create(); // créé un "builder" json qui rajoute les indents correctement (pretty print)
		String json = gson.toJson(BlockC); // converti la blockchain en JSON sous format String
		file = new File("./" + filename); // on créé le fichier dans le répertoire courant
		FileOutputStream fop = null; // on initialise l'outil pour ecrire dans le fichier
		try
		{
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e)
		{
			System.err.println("le fichier " + filename + " n'existe pas. vérifiez le nom et réessayez");
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
