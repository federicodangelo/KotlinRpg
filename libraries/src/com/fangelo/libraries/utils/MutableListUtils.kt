package com.fangelo.libraries.utils

object MutableListUtils {

    //Non-memory allocating nonAllocatingSort implementation, not as good as the java one, but produces 0 memory allocations!
    fun <T> nonAllocatingSort(a: MutableList<T>, comparator: Comparator<T>) {
        nonAllocatingSort(a, 0, a.size - 1, comparator)
    }

    //Converted from https://stackoverflow.com/questions/29609909/inplace-quicksort-in-java
    private fun <T> nonAllocatingSort(a: MutableList<T>, left: Int, right: Int, comparator: Comparator<T>) {
        if (right > left) {
            var i = left
            var j = right //we want j to be right, not right-1 since that leaves out a number during recursion

            var tmp: T

            val v = a[right] //pivot

            do {

                var cmpResult1 = comparator.compare(a[i], v)

                while (cmpResult1 < 0) {
                    i++
                    cmpResult1 = comparator.compare(a[i], v)
                }

                var cmpResult2 = comparator.compare(a[j], v)
                while (cmpResult2 > 0) {
                    j-- //no need to check for 0, the right condition for recursion is the 2 if statements below.
                    cmpResult2 = comparator.compare(a[j], v)
                }

                if (i <= j) {            //your code was i<j
                    tmp = a[i]
                    a[i] = a[j]
                    a[j] = tmp
                    i++
                    j--
                    //we need to +/- both i,j, else it will stick at 0 or be same number
                }
            } while (i <= j)           //your code was i<j, hence infinite loop on 0 case

            //you had a swap here, I don't think it's needed.
            //this is the 2 conditions we need to avoid infinite loops
            // check if left < j, if it isn't, it's already sorted. Done

            if (left < j)
                nonAllocatingSort(a, left, j, comparator)
            //check if i is less than right, if it isn't it's already sorted. Done
            // here i is now the 'middle index', the slice for divide and conquer.

            if (i < right)
                nonAllocatingSort(a, i, right, comparator)
        }
    }
}