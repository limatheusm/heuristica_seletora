import java.util.*;
import java.io.*;
import java.text.*;

class HeuristicMain {
	public static void main(String[] args) {
		BufferedReader inReader =
                       new BufferedReader(new InputStreamReader(System.in));
        String line;
        int j = 0;
        int size = 0;
        long begin = 0;

        try {       	

            /* Lendo primeira linha, vendo tamanho do vetor */
            size = Integer.parseInt(inReader.readLine());

            /* Verificar tipo de entrada a partir do segundo valor da entrada padrao */
            line = inReader.readLine();

            if(TypeTester.isNumeric(line)){

            	/******************* Eh do tipo NUMBER ********************/

            	long[] arrayInt = new long[size];
            	arrayInt[j] = Long.parseLong(line);
            	j++;

            	/* Preenchendo array */
	            while((line = inReader.readLine()) != null) {
	                arrayInt[j] = Long.parseLong(line);	             
	                j++;
	            }	
				
				/* Segue Heuristica */

				/* Calculando range (k) */
				long min = arrayInt[0];
			    long max = arrayInt[0];
		 		for (int i = 1; i < arrayInt.length; i++) {
		 			min = (arrayInt[i] < min) ? arrayInt[i] : min;
					max = (arrayInt[i] > max) ? arrayInt[i] : max;
		 		}

		 		long k = (max - min) + 1;
		 		
		 		/* Calculando qte de digitos do maior elemento (d) */
		 		int d = 0;
		 		for (int exp = 1; max/exp > 0; exp *= 10)
            		d++;

		 		//System.out.println("max: "+max+"\nmin: "+min+"\nrange (k): "+k+"\nnum de dig (d): "+d);
		 		//System.out.println("Number. Size : "+arrayInt.length);

		 		/* Primeira tentativa, analisando counting */

		 		
		 		/** Range Condition
		 		* range pro size
		 		* numero de instancias pequena (500.000) aceita o 1.5 * size como memoria aux
				* cc, aceita 50% do size
				*/
				boolean rangeCondition = false;
				int maxSize = 500000;
				if(size <= maxSize && k <= (size*1.5)){
					rangeCondition = true;
				}
				else if(size > maxSize && k <= (size*0.5)){
					rangeCondition = true;
				}

				//ja ordenado
		 		if(size == 0 || max == min){}					

		 		else if(rangeCondition && d > 4){
		 			//System.out.println(">>>> CountingSort <<<<");
		 			begin = System.currentTimeMillis();
		 			CountingSort.sort(arrayInt, max, min);
		 			//System.out.println("Time CountingSort: "+((System.currentTimeMillis()) - begin)+" ms");
		 		}

		 		else if(d <= 4){
		 			//System.out.println(">>>> RadixSort <<<<");
		 			begin = System.currentTimeMillis();
		 			RadixSort.sort(arrayInt, max, min);
		 			//System.out.println("Time RadixSort: "+((System.currentTimeMillis()) - begin)+" ms");
		 		}

		 		//test hash e calcular baldes vazios
		 		// numero de baldes usados VS baldes criados
		 		// % de baldes vazios

		 		else if(testHashBucket(size, max, min, arrayInt)){
		 			//System.out.println(">>>> BucketSort <<<<"); //muita estrutura dados
		 			begin = System.currentTimeMillis();
		 			BucketSort.sort(arrayInt, max, min);
		 			//System.out.println("Time BucketSort: "+((System.currentTimeMillis()) - begin)+" ms");
		 		}

		 		else{
		 			//System.out.println(">>>> Arrays <<<<");
		 			begin = System.currentTimeMillis();
		 			QuickDualPivot.sort(arrayInt);
		 			//System.out.println("Time Arrays: "+((System.currentTimeMillis()) - begin)+" ms");
		 		}			

	            /* Exibindo vetor */
	            for (int i = 0; i < arrayInt.length; i++) 
                	System.out.println(arrayInt[i]);

                
            }

            else{

            	/* Eh do tipo STRING */

            	String[] arrayString = new String[size];
            	arrayString[j] = line;
            	j++;

            	//Preenchendo o array
	            while((line = inReader.readLine()) != null) {
	            	arrayString[j] = line;              
	                j++;
	            }

	            SortString.sort(arrayString);

	            /* Exibindo vetor */
	            for (int i = 0; i < arrayString.length; i++) 
                	System.out.println(arrayString[i]);

            }

            inReader.close();              

        }catch (IOException e) {
            System.err.print(e.getMessage());
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
	}

	/**
	*	Função que testa a distribuição da entrada nos buckets
	*	caso a porcentagem de buckets vazios for < 20%, retorna true
	*	ou seja, esta bem distribuido. CC retorne false
	*
	*/
	private static boolean testHashBucket(int size, long max, long min, long[] vector){
		

		int numBuckets = (size < BucketSort.maxBuckets) ? size/2 : BucketSort.maxBuckets;
		//System.out.println("numBuckets: "+numBuckets);
		ArrayList<ArrayList<Long>> buckets = new ArrayList<ArrayList<Long>> (numBuckets);
		for(int i = 0; i < numBuckets; i++){
			//lista interna de cada balde (seu conteudo sera o conteudo de cada balde)
			buckets.add(new ArrayList<Long>());
		}

	/*
	* Range alto = possibilita maior colisao
	* Range baixo = possibilita menor colisao e dependendo da entrada, maior numero de buckets e poucos elementos em cada
	*
	*/
		double bucketRange = (((double)max - min + 1) / (numBuckets));

		int[] collisions = new int[numBuckets];
		Arrays.fill(collisions,-1);
		int emptyBuckets = 0;

	    for (int i = 0; i < size; ++i){
	        int bucket = (int)((vector[i] - min) / bucketRange);
	        buckets.get(bucket).add(vector[i]);
	        collisions[bucket]++;
	    }

	    for (int i = 0; i < collisions.length; i++) {
	    	if(collisions[i] == -1)
	    		emptyBuckets++;
	    }

	    //System.out.println("porcentagem de emptyBuckets: "+(((double)emptyBuckets / numBuckets) * 100)+" %");

	    if ( (((double)emptyBuckets / numBuckets) * 100) < 20.0) {
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}
}

class TypeTester {

    public static boolean isNumeric(String str){
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
}