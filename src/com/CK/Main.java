package com.CK;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] words = {"What", "must", "be", "acknowledgment", "shall", "be"};
        System.out.println(new Solution().fullJustify(words, 16).toString());
    }
}

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> list = new ArrayList<String>();

        int N = words.length;
        int right = 0;
        for (int left = 0; left < N; left = right) {
            // Each word comes with one space;
            // Except the first word, so start with -1.
            int len = -1;
            for (right = left; right < N && len + words[right].length() + 1 <= maxWidth; right++) {
                len += words[right].length() + 1;
            }

            // Those are in case there's only one word picked, or in last line
            int space = 1;
            int extra = 0;
            if (right != left + 1 && right != N) {
                // right - left - 1 is number of gaps
                space = (maxWidth - len) / (right - left - 1) + 1;
                extra = (maxWidth - len) % (right - left - 1);
            }
            StringBuilder sb = new StringBuilder(words[left]);
            for (int i = left + 1; i < right; i++) {
                for (int j = 0; j < space; j++) sb.append(' ');
                if (extra-- > 0) sb.append(' ');
                sb.append(words[i]);
            }

            int rightSpace = maxWidth - sb.length();
            while (rightSpace-- > 0) sb.append(' ');
            list.add(sb.toString());
        }

        return list;
    }
}


class Solution2 {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        if (words.length == 0) return res;

        int wIdx = 0;
        boolean isLast = false;

        while (wIdx < words.length) {
            int greedy = 0, wordLen = 0, spaceLen = 0, wordCnt = 0, spaceCnt = 0, currIdx = wIdx;
            StringBuilder sb = new StringBuilder();
            while (wIdx < words.length && greedy + words[wIdx].length() <= maxWidth) {
                greedy = greedy + words[wIdx].length() + 1;
                wordLen += words[wIdx].length();
                wIdx++;
                wordCnt++;
            }
            spaceLen = maxWidth - wordLen;
            spaceCnt = wordCnt - 1;

            if (wIdx >= words.length) isLast = true;

            if (isLast) {
                while (currIdx < words.length) {
                    sb.append(words[currIdx]);
                    if (sb.length() < maxWidth) sb.append(" ");
                    currIdx++;
                }
                while (sb.length() < maxWidth) sb.append(" ");
                res.add(sb.toString());
            } else {
                int eachLen = spaceCnt == 0 ? 0 : spaceLen / spaceCnt;
                int extra = spaceCnt == 0 ? 0 : spaceLen % spaceCnt;
                while (spaceCnt > 0) {
                    sb.append(words[currIdx]);
                    for (int i = 0; i < eachLen; i++) {
                        sb.append(" ");
                        spaceLen--;
                    }
                    if (extra-- > 0)
                        sb.append(" ");
                    currIdx++;
                    spaceCnt--;
                }
                sb.append(words[currIdx]);
                while (sb.length() < maxWidth) sb.append(" ");
                res.add(sb.toString());
            }

        }
        return res;
    }
}