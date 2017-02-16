public class QuickDualPivot{

  public static void sort (long[] input){
      sort (input, 0, input.length-1);
  }

  private static void sort(long[] input, int lowIndex, int highIndex) {

      if (highIndex <= lowIndex) return;
      
      long pivot1 = input[lowIndex];
      long pivot2 = input[highIndex];
      
      
      if (pivot1 > pivot2){
          swap(input, lowIndex, highIndex);
          pivot1 = input[lowIndex];
          pivot2 = input[highIndex];
      }
      else if (pivot1 == pivot2){
          while (pivot1 == pivot2 && lowIndex < highIndex){
              lowIndex++;
              pivot1 = input[lowIndex];
          }
      }
      

      int i = lowIndex + 1;
      int lt = lowIndex + 1;
      int gt = highIndex - 1;
      
      while (i <= gt){
          
          if ( less(input[i], pivot1) ){
              swap(input, i++, lt++);
          }
          else if (less(pivot2, input[i])){
              swap(input, i, gt--);
          }
          else{
              i++;
          }
          
      }
      
      
      swap(input, lowIndex, --lt);
      swap(input, highIndex, ++gt);
      
      sort(input, lowIndex, lt-1);
      sort (input, lt+1, gt-1);
      sort(input, gt+1, highIndex);
          
  }
  
  public static void swap(long[] A, int i, int j) {
         long tmp = A[i]; A[i] = A[j]; A[j] = tmp;
  }

  public static boolean less(long n1, long n2){
    return (n1 < n2) ? true : false;
  }
}