import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;
import java.util.Collections;

public class Minlexbfs {
	static class Task {
		public static final String INPUT_FILE = "minlexbfs.in";
		public static final String OUTPUT_FILE = "minlexbfs.out";
		public static final int NMAX = 100005;

		int N;
		int M;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		int source = 1;

		private void readInput() {
			try {
				FileReader file = new FileReader(INPUT_FILE);
				BufferedReader buffer = new BufferedReader(file);
				String line;
				StringTokenizer st;

				line = buffer.readLine();
				st = new StringTokenizer(line," ");
				N = parseInt(st.nextToken());
				M = parseInt(st.nextToken());

				for (int i = 1; i <= N; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= M; i++) {
					line = buffer.readLine();
					st = new StringTokenizer(line," ");
					int X, Y;
					X = parseInt(st.nextToken());
					Y = parseInt(st.nextToken());
					adj[X].add(Y);
					adj[Y].add(X);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int i = 1; i <= N; i++) {
					pw.printf("%d%c", result[i], i == N ? '\n' : ' ');
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int[] getResult() {
			int d[] = new int[N + 1];

			int minlex[] = new int [N + 1];

			ArrayList<Integer> nivel;

			for (int i = 1; i <= N; i++)
				d[i] = -1;

			int queue[] = new int[N + 1];
			int p, q;
			p = q = 0;

			// Adaugam sursa in coada, marcam distanta 0.
			queue[q] = source; q++;
			d[source] = 0;

			minlex[1] = source;

			int k = 2;

			// Cat timp coada mai are noduri,
			while (p < q) {
				// Scoatem primul nod din coada,
				int node = queue[p]; p++; 

				nivel = new ArrayList <Integer>();

				Collections.sort(adj[node]);
				for (int neighbor : adj[node]) {

					// Iteram prin vecinii lui
					if (d[neighbor] == -1) {
						// Daca sunt nevizitati, ii vizitam setandu-le distanta
						// ca fiind distanta pana la nodul curent + 1 (muchia pe care
						// o parcurgem sa ajungem din nodul curent la vecin).
						d[neighbor] = d[node] + 1;

						// Adaugam vecinul in coada.
						queue[q] = neighbor; q++;

						nivel.add(neighbor);
					}
	
				}
				
				for (int j = 0; j < nivel.size(); j++) {
					minlex[k] = nivel.get(j);
					k++;
				}
			}

			return minlex;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
