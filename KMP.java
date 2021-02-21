//这个算法的主要难点在于构建prefix数组
//prefix数组里面的数字很有趣，它代表着，之前和当前这一位上的值一样那一位的下一位，有点晕
//举个例子， ABAC，在这个string里，第0位和第2位的值相同，那么如果我们构建一个prefix数组的话，应该是{0,0,1,0}，第二位上的值prefix[2]是1，也就是第0位的下一位
//理解完prefix的构建原理之后，再看代码，这里我用两个Integer数组来代表，他们可以是任意type的数组，也可以string
//在构建prefix的compute method里面，我们目的是要找到和当前位有相同值的那位的下一位，所以我们要设置两个指针为0和1，这样就可以找错位的prefix，错位这个细节可以背下来，
//在找prefix的过程中，我们也会遇到，i和j无法匹配的情况，这种时候我们就可以用到构建了一半的prefix数组来找他们match的那段prefix，然后继续匹配 （套娃警告！ 这段用法很不错，也需要背诵）
//构建好prefix数组后，我们来进行匹配，这就很中规中矩了，设置俩指针都为0，然后不匹配的时候，我们让pat的指针回溯到之前已匹配的那位的下一位，
//这里面又有细节了，当前这位不匹配，那么我们要怎么进行回溯呢？直接找当前位实行不同的，因为在构建的时候，我们给prefix赋的值是相同值那位的下一位，也就是说，当前位不匹配的话，你要先把j给减1， 然后找减1后的prefix值，再赋值给j， 就变成了 j = prefix[j - 1]
//当j是0的时候就没办法了，只能让i加1，重头开始匹配了
//看到这里，你可能会有个疑惑，i直接加1没问题吗，万一在i和j刚开始匹配的时候，正好i的下一位和当时的j完全匹配的，其实这种情况是不存在的，因为当我们没有匹配上的时候，我们做的回溯相当于把pat数组右移，我都玩命右移没找到，那其实就不存在这种情况了
public int kmp (int[] pat, int[] nums) {
        int[] prefix = compute(pat);
        int i = 0, j = 0;
        while (i < nums.length && j < pat.length) {
            if (nums[i] == pat[j]) {
                i++;
                j++;
            }
            else {
                if (j == 0) {
                    i++;
                }
                else {
                    j = prefix[j - 1];
                }
            }
        }
        return j == pat.length ? i : -1;
    }
    
    public int[] compute (int[] pat) {
        int[] prefix = new int[pat.length];
        int i = 1;
        int j = 0;
        while (j < prefix.length && i < prefix.length) {
            if (pat[j] == pat[i]) {
                prefix[i] = j + 1;
                i++;
                j++;
            }
            else {
                if (j == 0)
                {
                    prefix[i] = 0;
                    i++;
                }
                else 
                    j = prefix[j - 1];
            }
        }
        return prefix;
    }