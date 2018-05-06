package niveau2;


import java.math.BigInteger;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
@SuppressWarnings("deprecation")
public class CreateAddress {
	/*methode qui, normalement, renvoie un tableau d' adresse de taile n passé en paramètre*/ 
	
	public static Address[] genererNAdresse(int n)
	{
		int i=0;
		final NetworkParameters netParams;
        netParams = NetworkParameters.testNet();
		Address[] tabAddress = new Address[n];
		for(i=0;i<n;i++)
		{
			ECKey key = new ECKey();
			tabAddress[i] = key.toAddress(netParams);
		}
		return tabAddress;
	}
	/** 
	 * revoie un tableau de clé pour signer les messages et générer les adresses
	 * @param n le nombre de clés à générer
	 * @return un tableau de clés 
	 */
	@SuppressWarnings("unused")
	public static ECKey[] genererNKey(int n)
	{
		
		int i=0;
		final NetworkParameters netParams;
        netParams = NetworkParameters.testNet();
        ECKey[] tabKey = new ECKey[n];
		for(i=0;i<n;i++)
		{
			tabKey[i] = new ECKey();
		}
		return tabKey;
	}
	/**
	 * converti un tableau de clé en un tableau d'adresses pour les transactions 
	 * @param tabKey un tableau de clé 
	 * @return un tableau d'adresses 
	 */
	public static Address[] keyToAdresse(ECKey[] tabKey)
	{
		int i=0;
		final NetworkParameters netParams;
        netParams = NetworkParameters.testNet();
		Address[] tabAddress = new Address[tabKey.length];
		for(ECKey cle : tabKey)
		{
			tabAddress[i] = cle.toAddress(netParams);
			i++;
		}
		return tabAddress;
	}
	/**
	 * @deprecated pas utilisé ici
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
    public static void adressTest() throws Exception {
  
          //use test net by default
          String net = "test";
         
          // create a new EC Key ...
          ECKey key = new ECKey(); // paire de clefs
          ECKey key2 = new ECKey(); // une autre
  
          // ... and look at the key pair
          System.out.println("We created key 1:\n" + key);
          System.out.println("We created key 2:\n" + key2);
          
          // either test or production net are possible
          final NetworkParameters netParams;
          netParams = NetworkParameters.testNet();
          
          // get valid Bitcoin address from public key
          Address addressFromKey = key.toAddress(netParams);
          Address addressFromKey2 = key2.toAddress(netParams);
          
          System.out.println("La clef 1 : "+key.toStringWithPrivate(netParams));
          System.out.println("La clef 2 : "+key2.toStringWithPrivate(netParams));
          
          System.out.println("On the " + net + " network, we can use this address 1:\n" + addressFromKey);
          System.out.println("On the " + net + " network, we can use this address 2:\n" + addressFromKey2);
          
          BigInteger pKey = key.getPrivKey();
          //System.out.println("Priv key 1 (Hex): "+key.getPrivateKeyAsHex());
		byte[] pubKey = key.getPubKey();
          System.out.println("Pub key 1 (HEX): "+key.getPublicKeyAsHex());
          String message = "Message Ã  signer";
          String msgSign = key.signMessage(message);
          System.out.println("Message signÃ© : "+msgSign);
          @SuppressWarnings("static-access")
		ECKey keyFromSign = new ECKey().signedMessageToKey(message,msgSign);
          System.out.println("ECKey sign : "+keyFromSign.getPublicKeyAsHex());
          System.out.println("ECKey  : "+key.getPublicKeyAsHex());
          if (keyFromSign.getPublicKeyAsHex().equals(key.getPublicKeyAsHex()))
              System.out.println("OK");
          else
              System.out.println("KO");
          
      }
  }