
class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int k = queries.length;

        int[][] q = new int[k][2];

        for (int i = 0; i < k; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        int[] ans = new int[k];

        Map<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int right = 0;

        for (int i = 0; i < k; i++) {

            int time = q[i][0];

            // Add logs within the current time window
            while (right < logs.length &&
                   logs[right][1] <= time) {

                int server = logs[right][0];

                map.put(server,
                    map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs outside the current window
            while (left < right &&
                   logs[left][1] < time - x) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            // Servers with zero requests
            ans[q[i][1]] = n - map.size();
        }

        return ans;
    }
}
