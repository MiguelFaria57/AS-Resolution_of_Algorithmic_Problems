#include <iostream>
#include <vector>

using namespace std;

// Structs
struct BoardCell {
    int pR;
    int pC;
    int piece[4];
    bool inserted;
};

struct Piece {
    int configs[4][4]{};
    bool used = false;
};


// Variables
int possible;

int n, r, c;

vector<int> countColors;                    // vector where the colors will be counted to see if the number is even
vector<BoardCell> board;                    // vector where the puzzle is going to be
vector<Piece> pieces;                       // vector where the pieces are going to be
vector<vector<vector<int>>> upComp;         // vector of 3 dimensions where the upside of all configs of a piece will be stored
vector<vector<vector<int>>> leftComp;       // vector of 3 dimensions where the left side of all configs of a piece will be stored


// Functions

/**
 * function to check the input directly in order to see if it's possible to create the puzzle
 * 
 */
void preCheckPossibility() {
    int oddColors = 0;

    int i;
    for (i=0; i<1000; i++) {
        if (countColors[i]%2 != 0)
            oddColors++;
    }
    if (oddColors > 4)
        possible = -1;
}


/**
 * initiale the board with the correct positions, according to the input 
 * 
 */
void initializeBoard() {
    int row=0, column=0;
    int i;
    for (i=0; i<int(board.size()); i++) {
        board[i].pR = row,
                board[i].pC = column;
        board[i].inserted = false;

        column = (column+1)%c;
        if (column == 0)
            row++;
    }
}

/**
 * print of the board in it's correct form
 * 
 */

void printBoard() {
    int i, j;
    int a = 0;
    for (i=0; i<2*r; i++) {
        int aux = a;
        for (j=0; j<c; j++) {
            if (i % 2 == 0)
                cout << board[aux].piece[0] << " " << board[aux].piece[1];
            else
                cout << board[aux].piece[3] << " " << board[aux].piece[2];

            if (j == c-1) {
                cout << "\n";
                if (i==2*r-1)
                    return;
                if (i%2 != 0) {
                    cout << "\n";
                    a += c;
                }
            }
            else {
                cout << "  ";
            }

            aux++;
        }
    }
}

/**
 * function to insert a piece in the board
 *
 * @param position the index on board array
 * @param number piece number
 * @param config piece configuration
 */

void insertPiece(int position, int number, int config) {
    int i;
    for (i=0; i<4; i++)
        board[position].piece[i] = pieces[number].configs[config][i];
    board[position].inserted = true;
    pieces[number].used = true;
}

/**
 * function to remove a piece from the board
 *
 * @param position the index on board array
 * @param number piece number
 */

void removePiece(int position, int number) {
    board[position].inserted = false;
    pieces[number].used = false;
}

/**
 * function to check the needed requirements in order to add pieces to the board (recursively)
 *
 * @param indexPiece the index on board array
 */

void addPieces(int indexPiece) {

    if (board[r*c-1].inserted) {        // check if the puzzle is complete
        possible = 1;
        return;
    }
    else {
        if (indexPiece < r * c) {
            BoardCell currentPiece = board[indexPiece];     //get the current spot to insert, for row and column purposes


            if (currentPiece.pR == 0) {         //check if the current piece to insert in the puzzle is in the first row
                if (!leftComp[board[indexPiece - 1].piece[1]][board[indexPiece - 1].piece[2]].empty()) {        //if the vector containing the needed pieces and rotations isn't empty
                    vector<int> compPieces = leftComp[board[indexPiece - 1].piece[1]][board[indexPiece - 1].piece[2]];
                    for (int i = 0; i < int(compPieces.size()) / 2; i++) {
                        if(!pieces[compPieces[2*i]].used) {
                            int pieceNumber = compPieces[2 * i];        //the vector has an even number of elements (piece, configuration)
                            int pieceConfig = compPieces[2 * i + 1];
                            insertPiece(indexPiece, pieceNumber, pieceConfig);         // recursion call for next piece
                            int aux = indexPiece + 1;
                            addPieces(aux);

                            if (possible == 1)                          // if the board has been complete with this piece
                                return;
                            else {
                                aux = indexPiece;                       //remove this piece since the board can't be completed with it in this position
                                removePiece(aux, pieceNumber);
                            }
                        }
                    }
                }
            }
            else if (currentPiece.pC == 0) {                            //check if the current piece to insert in the puzzle is in the first column
                if (!upComp[board[indexPiece - c].piece[3]][board[indexPiece - c].piece[2]].empty()) {
                    vector<int> compPieces = upComp[board[indexPiece - c].piece[3]][board[indexPiece - c].piece[2]];
                    for (int i = 0; i < int(compPieces.size()) / 2; i++) {
                        if(!pieces[compPieces[2*i]].used) {
                            int pieceNumber = compPieces[2 * i];
                            int pieceConfig = compPieces[2 * i + 1];
                            insertPiece(indexPiece, pieceNumber, pieceConfig);
                            int aux = indexPiece + 1;
                            addPieces(aux);

                            if (possible == 1)
                                return;
                            else {
                                aux = indexPiece;
                                removePiece(aux, pieceNumber);
                            }
                        }
                    }
                }
            }
            else { //the current piece to insert needs to check its upper and left pieces

                // neither of the vectors is empty, otherwise there are no pieces
                if(!leftComp[board[indexPiece - 1].piece[1]][board[indexPiece - 1].piece[2]].empty() && !upComp[board[indexPiece - c].piece[3]][board[indexPiece - c].piece[2]].empty()){
                    vector<int> compPiecesUp = upComp[board[indexPiece - c].piece[3]][board[indexPiece - c].piece[2]];
                    for (int i = 0; i < int(compPiecesUp.size()) / 2; i++) {
                        if(!pieces[compPiecesUp[2*i]].used) {
                            vector<int> compPiecesLeft = leftComp[board[indexPiece - 1].piece[1]][board[indexPiece - 1].piece[2]];
                            for (int j = 0; j < int(compPiecesLeft.size()) / 2; j++) {
                                if (compPiecesUp[2 * i] < compPiecesLeft[2 * j])        // since the pieces have their number assigned by order, if the piece number in leftC is greater
                                                                                        // than the piece number in upC, change piece
                                    break;
                                if (compPiecesUp[2 * i] == compPiecesLeft[2 * j] && compPiecesUp[2 * i + 1] == compPiecesLeft[2 * j + 1]) { // same piece number and configuration, means that the piece fits
                                    int pieceNumber = compPiecesUp[2 * i];
                                    int pieceConfig = compPiecesUp[2 * i + 1];
                                    insertPiece(indexPiece, pieceNumber, pieceConfig);
                                    int aux = indexPiece + 1;
                                    addPieces(aux);

                                    if (possible == 1)
                                        return;
                                    else {
                                        aux = indexPiece;
                                        removePiece(aux, pieceNumber);
                                    }
                                }
                            }
                        }
                    }
                    if (possible != 0)
                        return;
                }
            }
        }
    }
}

// Main
int main() {
    int num_tests;
    cin >> num_tests;

    int i, j, l;
    for (i=0; i<num_tests; i++) {
        cin >> n;
        if (n<1 || n>2500)
            return 1;
        cin >> r;
        if (r<1 || r>50)
            return 1;
        cin >> c;
        if (c<1 || c>50)
            return 1;
        if (n != r*c)
            return 1;

        vector<int> countColorsAux(1000);
        countColors = countColorsAux;
        vector<BoardCell> boardAux(r*c);
        board = boardAux;
        vector<vector<vector<int>>> upCompAux(999, vector<vector<int>> (999));        
        upComp = upCompAux;
        vector<vector<vector<int>>> leftCompAux(999, vector<vector<int>> (999));
        leftComp = leftCompAux;
        possible = 0;

        for (j=0; j<n; j++) {       
            Piece p;
            int aux;
            for (l=0; l<4; l++) {
                cin >> aux;
                if (aux>=0 && aux<=999) {    
                    countColors[aux]++;
                    p.configs[0][l] = aux;
                }
            }
            pieces.push_back(p);    // insert pieces in pieces array

            for (l=0; l<4; l++) {       // insert all configs in pieces array 
                if (l>0) {
                    aux = pieces[j].configs[l - 1][0];
                    pieces[j].configs[l][0] = pieces[j].configs[l - 1][1];
                    pieces[j].configs[l][1] = pieces[j].configs[l - 1][2];
                    pieces[j].configs[l][2] = pieces[j].configs[l - 1][3];
                    pieces[j].configs[l][3] = aux;
                }

                if (j != 0) {
                    upComp[pieces[j].configs[l][0]][pieces[j].configs[l][1]].push_back(j);
                    upComp[pieces[j].configs[l][0]][pieces[j].configs[l][1]].push_back(l);

                    leftComp[pieces[j].configs[l][0]][pieces[j].configs[l][3]].push_back(j);
                    leftComp[pieces[j].configs[l][0]][pieces[j].configs[l][3]].push_back(l);
                }
            }
        }
        preCheckPossibility();      

        if (possible == 0) {
            initializeBoard();
            insertPiece(0, 0, 0);
            addPieces(1);
        }

        if (possible == 1)
            printBoard();

        else
            cout << "impossible puzzle!\n";

        countColors.clear();
        board.clear();
        pieces.clear();
        upComp.clear();
        leftComp.clear();
    }


    return 0;
}