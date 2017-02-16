class CountingSort {
	

	public static void sort(long vector[], long maxLong, long minLong){
		
		int i;
		int[] sorted = new int[vector.length];
			    	
		int max = (int) maxLong;
		int min = (int) minLong;

		//cria vector count de tamanho do maior elemento do vector a ser ordenado
		int count[] = new int[max - min + 1];
		
		//contador de ocorrencias de cada elemento
		for(i = 0; i < vector.length; i++)
			count[(int)vector[i] - min]++;
		
		//acumulador 
		count[0]--;
		for (i = 1; i < count.length; i++ )
			count[i] += count[i - 1];	 

		//ordena saida
		for (i = 0; i < sorted.length; i++) {
			sorted[count[(int)vector[i] - min]--] = (int) vector[i];
		}

		//atualiza vector de entrada
		for (i = 0; i < sorted.length; i++) 
			vector[i] = sorted[i];
		
	}

}