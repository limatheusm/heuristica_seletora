import java.util.*;

class SortString {
		/* Radix sort */
	 public static void sort( String[] arr){
        final int BUCKETS = 256;
        	
        int maxLen = arr[0].length();

    	for (int i = 1; i < arr.length ; i++ ) {
			maxLen = (arr[i].length() > maxLen) ? arr[i].length() : maxLen;    		
    	}

    	/**
    	* wordsByLength = distribui as palavaras de acordo com o numero de caracters
    	*	ou seja, palavras com mesmo numero de caracteres ocuparão o msm indice
    	*/

        ArrayList<ArrayList<String>> wordsByLength = new ArrayList<ArrayList<String>> (maxLen + 1);
        ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>> (BUCKETS);
        
        /* Instancia os arraysLists internos */
        for( int i = 0; i < (maxLen + 1); i++ )
        	wordsByLength.add(new ArrayList<String>());
        
        for( int i = 0; i < BUCKETS; i++ )
            buckets.add(new ArrayList<String>());

        /* Preenche na posicao de acordo com seu tamanho */
        for( String s : arr )
        	wordsByLength.get(s.length()).add(s);
       
        int index = 0;
        /* Ordena primeiro de acordo com o tamanho de cada palavra */
        for( ArrayList<String> wordList : wordsByLength )
            for( String s : wordList )
                arr[ index++ ] = s;
        /* 
        Processo de ordenacao
			Ordenação LSD - ordena os caracteres menos significativos
         */
        int startIndex = arr.length;    
        for( int pos = maxLen - 1; pos >= 0; pos-- ){

            startIndex -= wordsByLength.get(pos + 1).size();
            
            /* Recupera o valor ASCII do caracter atual (charAt[pos]) 
            	e adiciona no bucket correspondente */
            for( int i = startIndex; i < arr.length; i++ )
                buckets.get( arr[i].charAt(pos) ).add(arr[i]);
         
            /* Adiciona string na posição correta, de acordo com a ordenação
            do caracter da vez */
            index = startIndex;
            for( ArrayList<String> thisBucket : buckets ){
                for( String s : thisBucket )
                    arr[ index++ ] = s;               
                
                thisBucket.clear();
            }
        }
    }
}