class Solution {
    public int scoreOfParentheses(String s) {
        int d = 0;
        int score = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '('){
                d++;
            }
            else{
                d--;
                if(s.charAt(i-1) == '('){
                    score += 1<<d;
                }
            }
        }
        return score;
    }
}