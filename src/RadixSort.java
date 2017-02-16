import java.util.*;

class RadixSort {
    
    //Faz contagem de acordo com o dígito representado por exp.
    //
    private static void countingSort(long vector[], int exp){
        int size = vector.length;
        int sorted[] = new int[size]; // sorted vector
        int count[] = new int[10];
        Arrays.fill(count,0); //preenche com zeros
        
        // armazena acumulador de ocorrencia de digitos em count[]
        // (vector[i]/exp)%10 -> Recupera o digito atual da iteracao
        for (int i = 0; i < size; i++){
            count[ (int) (vector[i]/exp)%10 ]++;
        }
 
        // Acumulador para conter posicao ordenada autal do digito
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];
 
        // Ordena o vetor de acordo com o digito atual da iteracao
        for (int i = size - 1; i >= 0; i--){
            sorted[ count[ (int) (vector[i]/exp)%10] - 1 ] = (int) vector[i];
            count[ (int) (vector[i]/exp)%10 ]--;
        }
 
        //copia o array com os digitos atuais da iteracao ordenados
        for (int i = 0; i < size; i++)
            vector[i] = sorted[i];      

    }
 
    // main
    public static void sort(long vector[], long max, long min){        
 
        // Ordenar countingSort para cada digito.
        // Caso chegue no fim do digito de maior valor, fim do laco
        // exp eh 10^i e i corresponde ao digito da iteracao atual   

        for (int i = 0; i < vector.length; i++)
            vector[i]+=Math.abs(min);

        max+=Math.abs(min);

        for (int exp = 1; max/exp > 0; exp *= 10)
            countingSort(vector, exp);

        for (int i = 0; i < vector.length; i++)
            vector[i]-=Math.abs(min);
    }
}