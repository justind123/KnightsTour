use std::time::Instant;

const SIZE: i32 = 5;
const DY: [i32; 8] = [2, 1, -1, -2, -2, -1, 1, 2];
const DX: [i32; 8] = [1, 2, 2, 1, -1, -2, -2, -1];

fn is_valid(x: i32, y: i32, board: [i32; (SIZE * SIZE) as usize]) -> bool {
    if x < 0 || y < 0 || x >= SIZE || y >= SIZE {
        return false;
    }
    let index = (y * SIZE + x) as usize;
    if board[index] != 0 {
        return false;
    }
    
    return true;
}

fn solve(x: i32, y: i32, curr_move: i32, mut solutions: i32, mut board: [i32; (SIZE * SIZE) as usize]) -> i32 {
    let index = (y * SIZE + x) as usize;
    board[index] = curr_move;

    if curr_move >= SIZE * SIZE {
        board[index] = 0;
        return solutions + 1;
    }

    for i in 0..8 {
        let next_x = x + DX[i];
        let next_y = y + DY[i];

        if is_valid(next_x, next_y, board) {
            solutions = solve(next_x, next_y, curr_move + 1, solutions, board);
        }
    }

    board[index] = 0;

    return solutions;
}

fn solve_helper(i: i32, j: i32) -> i32 {
    //println!("{} {}", i, j);
    let board = [0; (SIZE * SIZE) as usize];

    let solutions = solve(j, i, 1, 0, board);


    return solutions
}

fn main() {
    let rows_half = (SIZE as f64 / 2.0).ceil() as i32;
    let cols_half = (SIZE as f64 / 2.0).ceil() as i32;
    //println!("{} {}", rows_half, cols_half);

    let mut num_solutions = [0; (SIZE * SIZE) as usize];
    let mut total_solutions = 0;

    let now = Instant::now();
    for i in 0..rows_half {
        for j in 0..cols_half {
            let index = (i * SIZE + j) as usize;
            let solutions = solve_helper(i, j);
            num_solutions[index] = solutions;
            num_solutions[(SIZE * (SIZE - i - 1) + j) as usize] = solutions;
            num_solutions[(SIZE * (i) + (SIZE - j - 1)) as usize] = solutions;
            num_solutions[(SIZE * (SIZE - i - 1) + (SIZE - j - 1)) as usize] = solutions;
            total_solutions += solutions;

        }
    }
    let elapsed = now.elapsed();

    for i in 0..SIZE {
        for j in 0..SIZE {
            print!("{} ", num_solutions[(i * SIZE + j) as usize]);
        }
        println!();
    }
    println!("{} solutions", total_solutions);
    println!("{:?}", elapsed);
}