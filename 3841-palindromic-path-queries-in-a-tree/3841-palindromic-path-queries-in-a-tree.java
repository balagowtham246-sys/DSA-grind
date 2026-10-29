
import java.util.*;

class Solution {
    private List<Integer>[] graph;
    private int[][] up;
    private int[] depth, tin, tout;
    private int timer;
    private Fenwick bit;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];

        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] stack = new int[2 * n];
        int top = 0;
        stack[top++] = 0;

        while (top > 0) {
            int node = stack[--top];

            if (node >= 0) {
                tin[node] = ++timer;
                stack[top++] = ~node;

                for (int next : graph[node]) {
                    if (next == parent[node]) continue;

                    parent[next] = node;
                    depth[next] = depth[node] + 1;
                    up[0][next] = node;

                    for (int j = 1; j < LOG; j++) {
                        up[j][next] = up[j - 1][up[j - 1][next]];
                    }

                    stack[top++] = next;
                }
            } else {
                tout[~node] = timer;
            }
        }

        char[] chars = s.toCharArray();
        bit = new Fenwick(n + 1);

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            bit.update(tin[i], mask);
            bit.update(tout[i] + 1, mask);
        }

        List<Boolean> answers = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.trim().split("\\s+");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = (1 << (chars[u] - 'a'))
                          ^ (1 << (c - 'a'));

                bit.update(tin[u], delta);
                bit.update(tout[u] + 1, delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int ancestor = lca(u, v);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[ancestor] - 'a'));

                answers.add((mask & (mask - 1)) == 0);
            }
        }

        return answers;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < up.length; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = up.length - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    private static class Fenwick {
        private final int[] tree;

        Fenwick(int n) {
            tree = new int[n + 2];
        }

        void update(int index, int value) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i] ^= value;
            }
        }

        int query(int index) {
            int result = 0;
            for (int i = index; i > 0; i -= i & -i) {
                result ^= tree[i];
            }
            return result;
        }
    }
}
