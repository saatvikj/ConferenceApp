package MayLongChallenge;

import java.util.Arrays;
import java.util.Scanner;

public class MTYFRI {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int test = sc.nextInt();
		for(int i = 0; i < test; i++) {
			int n = sc.nextInt();
			int swapsAllowed = sc.nextInt();
			int[] arr = new int[n];
			for(int j = 0; j < n; j++) {
				arr[j] = sc.nextInt();
			}
			
			int currSwaps = 0;
			int flag = 0;

			for(int count=0; count < swapsAllowed; count++) {
				int sumMotu = arr[0];
				int sumTomu = 0;
				int maxEven = 0;
				int posEven = 0;
				int minOdd = Integer.MAX_VALUE;
				int posOdd = 0;
				for(int ind=1; ind < arr.length; ind++) {
			
					if(currSwaps < swapsAllowed) {
						if(ind%2 == 1) {
							if(arr[ind] < minOdd) {
								minOdd = arr[ind];
								posOdd = ind;
							}
						}
						else {
							if(arr[ind] > maxEven) {
								maxEven = arr[ind];
								posEven = ind;
							}
						}
						
					}
					
				}
				int temp = arr[posEven];
				arr[posEven] = arr[posOdd];
				arr[posOdd] = temp;
				currSwaps = currSwaps + 1;
				
				//System.out.println(Arrays.toString(arr));
				for(int in=1; in < arr.length; in++) {
					if(in%2 == 1) {
						sumTomu = sumTomu + arr[in];
					}
					else {
						sumMotu = sumMotu + arr[in];
					}
				}
				//System.out.println(sumTomu);
				//System.out.println(sumMotu);
				if(sumTomu > sumMotu) {
					flag = 1;
					break;
				}
				
				
			}
			if(flag == 1) {
				System.out.println("YES");
			}
			else {
				System.out.println("NO");
			}
			
		}
		
	}

}
