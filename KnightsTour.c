#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <sys/timeb.h>
#include <windows.h>

#define N 5

int numSolutions[N * N];
int dy[] = {2, 1, -1, -2, -2, -1, 1, 2};
int dx[] = {1, 2, 2, 1, -1, -2, -2, -1};

int isValid(int x, int y, int board[]) {
    if (x < 0 || y < 0 || x >= N || y >= N) {
            return 0;
        }
        if (board[y * N + x] != 0) {
            return 0;
        }

        return 1;
}

int solve(int x, int y, int currMove, int solutions, int board[]) {
    board[y * N + x] = currMove;

    if (currMove >= N * N) {
        board[y * N + x] = 0;
        return solutions + 1;
    }

    for (int i = 0; i < 8; i++) {
        int nextX = x + dx[i];
        int nextY = y + dy[i];

        if (isValid(nextX, nextY, board)) {
            solutions = solve(nextX, nextY, currMove + 1, solutions, board);
        }
    }

    board[y * N + x] = 0;

    return solutions;
}

DWORD WINAPI foo(LPVOID lpParameter) {
    int i = *((unsigned int*) lpParameter);

    int board[N * N];
    int x = i % N;
    int y = i / N;
    int solutions = solve(x, y, 1, 0, board);

    numSolutions[i] = solutions;
    numSolutions[N * (N - y - 1) + x] = solutions;
    numSolutions[N * (y) + (N - x -1)] = solutions;
    numSolutions[N * (N - y - 1) + (N - x - 1)] = solutions;

    printf("%d, %d solutions: %d\n", x, y, solutions);

    return 0;
}




int main() {

    int rowsHalf = ceil(N / 2.0);
    int colsHalf = ceil(N / 2.0);
    HANDLE threads[N * N];

    struct timeb start, end;
    ftime(&start);

    for (int i = 0; i < rowsHalf; i++) {
        for (int j = 0; j < colsHalf; j++) {
            int *spaceNum = malloc(sizeof(int));
            *spaceNum = i * N + j;
            HANDLE thread = CreateThread(
                NULL,   // default security attributes
                0,      // use default stack size
                foo,    // thread function name
                spaceNum,   // argument to thread function
                0,      // user default creation flags
                NULL);  // returns the thread identifier
            
            threads[i * N + j] = thread;
        }
    }
    
    for (int i = 0; i < N * N; i++) {
        WaitForSingleObject(threads[i], INFINITE);
    }
   
    ftime(&end);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf("%d ", numSolutions[i * N + j]);
        }
        printf("\n");
    }
    printf("\n");
    printf("took %lu ms\n", 1000 * (end.time - start.time) + (end.millitm - start.millitm));

    return 0;
}