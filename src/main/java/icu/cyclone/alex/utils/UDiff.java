package icu.cyclone.alex.utils;

public class UDiff {
    public static <T> int differences(T[] arr1, T[] arr2) {
        int m = arr1.length;
        int n = arr2.length;
        int[] d1;
        int[] d2 = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            d2[i] = i;
        }

        for (int i = 1; i <= m; i++) {
            d1 = d2;
            d2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) d2[j] = i;
                else {
                    int cost = arr1[i - 1].equals(arr2[j - 1]) ? 0 : 1;
                    if (d2[j - 1] < d1[j] && d2[j - 1] < d1[j - 1] + cost)
                        d2[j] = d2[j - 1] + 1;
                    else if (d1[j] < d1[j - 1] + cost)
                        d2[j] = d1[j] + 1;
                    else
                        d2[j] = d1[j - 1] + cost;
                }
            }
        }
        return d2[n];
    }

}
