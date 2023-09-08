#include <iostream>
#include <vector>
#include <iterator>
#include <sstream>
#include <unordered_map>

using namespace std;



// Structs
struct iterations{  // struct used to return the values from the findMembersAndAmount function
    vector<vector<int>> last;  // bi-dimensional vector containing the one/two best options from the last call
    int auxCase{};  // auxiliary variable to help decide which case is applied in the cases function
};

struct node {  // struct used to store the information about a person
    vector<int> sons;  // vector containing the members recruited by a person
    int value = -1;  //  value of the money paid
};


// Variables
unordered_map<int, node> people;  // unordered map used to store all people, that contains the number of a person as key and the person information as value


// Functions

/**
 * Function to get best values for a certain level of a branch
 * @param n current node
 * @param son current node's son best options and case
 * @return best valid options for the node
 */
vector<vector<int>> cases(node n, iterations son) {
    vector<vector<int>> branches;

    if (son.auxCase == 0) {  // level above a leaf
        branches.push_back({1, n.value});
        branches.push_back(son.last[0]);
    }
    else if (son.auxCase == 1) {  // odd level
        branches.push_back(son.last[0]);
        if(son.last.size()==1){
            branches.push_back({son.last[0][0] + 1, son.last[0][1] + n.value});
        }
        else {
            if (son.last[0][1] > son.last[1][1])
                branches.push_back({son.last[0][0] + 1, son.last[0][1] + n.value});
            else
                branches.push_back({son.last[1][0] + 1, son.last[1][1] + n.value});
        }
    }
    else { // even level
        if(son.last.size()==1){
            branches.push_back({son.last[0][0] + 1, son.last[0][1] + n.value});
        }
        else {
            branches.push_back({son.last[0][0] + 1, son.last[0][1] + n.value});
            branches.push_back({son.last[1]});
        }
    }

    return branches;
}


/**
 * recursive function to get the one/two best options of the node n
 * @param n current node
 * @return best options and case for the node
 */
iterations findMembersAndAmount(node n) {
    if (n.sons.empty()) {  // if leaf
        iterations current;
        current.last= {{1, n.value}};
        current.auxCase = 0;

        return current;
    }
    else{  // all the other cases
        int i;
        vector<vector<int>> branches;  // used to store the value from cases function
        unordered_map<int, vector<vector<vector<int>>>> temp;  // temporary unordered map used to store the answers of the even (2) and odd (1) branches
        vector<int> tempResult1 = {0, 0};  // used to store the best option of all branches with the father in it
        vector<int> tempResult2 = {0, 0};  // used to store the best option of all branches without the father in it

        for (int & son : n.sons) {  // get the answer for each branch
            iterations current = findMembersAndAmount(people[son]);  // recursive call
            branches = cases(n, current);  // solution of the branch, including the father

            if(current.auxCase == 0)
                current.auxCase = 2;

            // insert in the map
            if (temp.find(current.auxCase) == temp.end())
                temp[current.auxCase] = {branches};
            else
                temp[current.auxCase].push_back(branches);
        }

        for (i = 0; i < (int)temp[2].size(); i++) {  // get even branches and store the best option with or without the father
            tempResult1[0] += temp[2][i][0][0];
            tempResult1[1] += temp[2][i][0][1];

            if (i != 0) {
                tempResult1[0] -= 1;
                tempResult1[1] -= n.value;
            }

            if ((int) temp[2][i].size() == 2) {
                tempResult2[0] += temp[2][i][1][0];
                tempResult2[1] += temp[2][i][1][1];
            }
        }

        for (i = 0; i < (int)temp[1].size(); i++) {  // get odd branches and store the best option with or without the father
            if ((int) temp[1][i].size() == 2){
                if (tempResult1[0] == 0) {
                    tempResult1[0] += temp[1][i][1][0];
                    tempResult1[1] += temp[1][i][1][1];
                } else {
                    tempResult1[0] += temp[1][i][1][0] - 1;
                    tempResult1[1] += temp[1][i][1][1] - n.value;
                }
            }

            tempResult2[0] += temp[1][i][0][0];
            tempResult2[1] += temp[1][i][0][1];
        }

        iterations mine; // current node iteration for the return

        if(tempResult1[0]<tempResult2[0]){  // if the solution with the father has a lower number of nodes, it's the only one stored
            mine.last.push_back(tempResult1);
            mine.auxCase = 1;
        }

        else if(tempResult1[0]==tempResult2[0]){  // if both have the same number of nodes, it is an even branch so the father is in the first position
            mine.last.push_back(tempResult1);
            mine.last.push_back(tempResult2);
            mine.auxCase = 1;
        }
        else{  // if they have a different number of nodes, it is an odd branch so the father is in the second position
            mine.last.push_back(tempResult2);
            mine.last.push_back(tempResult1);
            mine.auxCase = 2;
        }

        return mine;
    }
}



// Main
int main() {
    bool zeroExists = false;

    string line;
    while (getline(cin, line)) {  // get a line
        // separate the numbers
        istringstream strStream(line);
        istream_iterator<int> begin(strStream), eof;
        vector<int> numbers(begin, eof);

        // get node information
        bool conditions = numbers[0]>=0 && numbers[0]<=100000 && numbers.size()<=12 && numbers.size()>=2 /*&& numbers[numbers.size()-1]>=0 && numbers[numbers.size()-1]<=100*/;
        if (conditions) {
            if (people.find(numbers[0]) == people.end()) {
                node aux;
                aux.value = numbers[numbers.size()-1];

                for (int i=1; i<(int)numbers.size()-1; i++) {
                    aux.sons.push_back(numbers[i]);
                }

                if (numbers[0] == 0)
                    zeroExists = true;
                people[numbers[0]] = aux;
            }
        }


        int test = -1;
        if (numbers[0] == -1) {
            if (zeroExists) {
                for(int & son : people[0].sons){  // see if we can start the program
                    if(people[son].value != -1){
                        test = 0;
                        break;
                    }
                }
                if(test==0){
                    iterations zero = findMembersAndAmount(people[0]);

                    int result[2];
                    if (zero.last.size() == 1) {  // if the function only returns one answer, we use it
                        result[0] = zero.last[0][0];
                        result[1] = zero.last[0][1];
                    } else {  // otherwise, we look for the best answer and use it
                        if (zero.last[0][0] < zero.last[1][0]) {
                            result[0] = zero.last[0][0];
                            result[1] = zero.last[0][1];
                        } else if (zero.last[0][0] > zero.last[1][0]) {
                            result[0] = zero.last[1][0];
                            result[1] = zero.last[1][1];
                        } else {
                            if (zero.last[0][1] > zero.last[1][1]) {
                                result[0] = zero.last[0][0];
                                result[1] = zero.last[0][1];
                            } else {
                                result[0] = zero.last[1][0];
                                result[1] = zero.last[1][1];
                            }
                        }
                    }
                    cout << result[0] << " " << result[1] << "\n";
                }else
                    cout <<"0 0\n";
            }
            else {
                cout << "0 0\n";
            }

            // reset variables
            zeroExists = false;
            people.clear();
        }
    }
    return 0;
}