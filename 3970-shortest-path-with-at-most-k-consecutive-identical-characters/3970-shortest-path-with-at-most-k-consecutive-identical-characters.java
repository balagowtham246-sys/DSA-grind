class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int[] e : edges) g[e[0]].add(new int[]{e[1], e[2]});

        int INF = Integer.MAX_VALUE;
        int[][] dist = new int[n][k + 1];
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], INF);
        dist[0][1] = 0;

        // (distance, node, run_length)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1], run = cur[2];
            if (u == n - 1) return d;
            if (d > dist[u][run]) continue;

            for (int[] e : g[u]) {
                int v = e[0], w = e[1];
                int next = labels.charAt(v) == labels.charAt(u) ? run + 1 : 1;
                if (next <= k && d + w < dist[v][next]) {
                    dist[v][next] = d + w;
                    pq.offer(new int[]{d + w, v, next});
                }
            }
        }
        return -1;
    }
}   