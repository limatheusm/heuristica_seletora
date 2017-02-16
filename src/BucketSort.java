import java.util.*;

class BucketSort {
	
	public static final int maxBuckets = 10000;

	public static void sort(long[] vector, long max, long min){

		int size = vector.length;
		/*Calcula menor e maior valor no vetor*/

 		//ja ordenado
 		if(size == 0 || max == min)
			return;

		
		int numBuckets = (size < maxBuckets) ? size/2 : maxBuckets;
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
		//System.out.println("bucketRange: "+bucketRange);


	    for (int i = 0; i < size; ++i){
	        int bucket = (int)((vector[i] - min) / bucketRange);
	        buckets.get(bucket).add(vector[i]);
	    }

	    //System.out.println(buckets);

	    //Ordena e atualiza
	    Comparator<Long> c = new Compara();
	    for(ArrayList<Long> bucket : buckets){
            bucket.sort(c);
        }

		int index = 0;
		for (int i = 0; i < numBuckets; i++){	
			// Por fim, o novo vetor eh populado usando a sequencia ordenada
			for (int j = 0; j < buckets.get(i).size(); j++){
				vector[index] = buckets.get(i).get(j);
				index++;
			}

		}
	}
}

class Compara implements Comparator <Long>{

	    public int compare(Long lvalue, Long rvalue) {
	        if(lvalue < rvalue)
	            return -1;
	        if(lvalue > rvalue)
	            return 1;
	       
	        return 0;
	    }
	}

