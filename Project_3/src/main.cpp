#include <iostream>
#include <vector>
#include <queue>
#include <set>

using namespace std;


// Structs
struct operation {
    int time{};  // T - time needed to process the operation
    int dependencies{};  // D - number of dependencies for this operation
    vector<int> operationsDependent;  // nodes that depend on the current one
    bool isLast = true;  // know if the node is final
    bool firstPass = true;  // know if it is the first time passing on the node (for statistic 3)
};


// Variables
vector<operation> pipeline;  // vector where all operations of the pipeline will be stored
set<int> connectedNodes;  // set to help verify if all operations are connected


// Functions
/**
 * @brief - function to detect if there are any cycles and if the graph is all connected
 *
 * @param current - index of the current node
 * @param visited - boolean value that indicates if the node has been previously visited
 * @param onStack - boolean value that indicates if the has been passed in the current recursion
 *
 * @return true - if there are cycles or the graph isn't connected
 * @return false - if there are no cycles
 */
bool checkCyclesAndConnection(int current, bool visited[], bool onStack[]){
    visited[current] = true;
    onStack[current] = true;
    for(int adjacent: pipeline[current].operationsDependent) {
        if(!visited[adjacent]) {
            connectedNodes.insert(adjacent);

            if(checkCyclesAndConnection(adjacent, visited, onStack)) {
                return true;
            }
        }
        else if (onStack[adjacent]) {
            return true;
        }
    }

    onStack[current] = false;
    return false;
}


/**
 * @brief - function to see if the pipeline is valid or not
 *
 * @param numOperations - total number of operations
 * @param disconectedNodes - bool that is true if there are disconnected nodes
 * @param initialNodes - variable to know if there is only one initial node and, if so, its index
 * @param finalNodes - number of final nodes
 *
 * @return true - if pipeline is valid
 * @return false - if pipeline is invalid
 */
bool validatePipeline(int numOperations, bool disconectedNodes, int initialNodes, int finalNodes) {
    // check initial nodes and disconected nodes
    if (disconectedNodes || initialNodes==-1 || pipeline[initialNodes].isLast) {  // see if there's disconected nodes, none initial node or a possible initial node that isn't connected
        return false;
    }

    // check final nodes
    if (finalNodes != 1) {
        return false;
    }

    // check if graph has cycles
    bool *visited = new bool[numOperations]{};
    bool *onStack = new bool[numOperations]{};
    connectedNodes.insert(initialNodes);
    if (checkCyclesAndConnection(initialNodes, visited, onStack) || (int)connectedNodes.size()!=numOperations) {
        delete[] visited;
        delete[] onStack;
        return false;
    }
    delete[] visited;
    delete[] onStack;

    // pipeline is valid
    return true;
}



/**
 * @brief - function for the first statistic, based on the Topological Sorting Algorithm
 *
 * @param initialNode - index of the initial node
 * @param totalTime - total time needed to process all operations, separately
 */
void statistic1(int initialNode, int totalTime) {
    cout << totalTime << "\n";

    priority_queue <int, vector<int>, greater<int> > q;             // Priority queue in ascending order, lower index comes first
    q.push(initialNode);

    while (!q.empty()) {
        int u = q.top();
        q.pop();
        cout << u+1 << "\n";

        for (int i: pipeline[u].operationsDependent) {
            pipeline[i].dependencies -= 1;
            if (pipeline[i].dependencies == 0) {
                q.push(i);
            }
        }
    }
}


/**
 * @brief - function for the second statistic, based on the Dijkstra Algorithm, reversed
 *
 * @param numOperations - total number of operations
 * @param initialNode - index of the initial node
 */
void statistic2 (int numOperations, int initialNode) {
    int finalNode = -1;
    int *path = new int[numOperations]{-1};

    queue<int> q;
    q.push(initialNode);
    path[initialNode] = pipeline[initialNode].time;

    while(!q.empty()){
        int current = q.front();
        q.pop();

        if(pipeline[current].isLast)
            finalNode = current;

        for(int i = 0; i < (int)pipeline[current].operationsDependent.size(); i++){
            int adjacent = pipeline[current].operationsDependent[i];
            if(path[adjacent] < path[current] + pipeline[adjacent].time){
                path[adjacent] = path[current] + pipeline[adjacent].time;
                q.push(adjacent);
            }
        }
    }

    cout << path[finalNode] << "\n";
    delete[] path;
}

/**
 * @brief - function for the third statistic, based on the BFS Algorithm
 *
 * @param initialNode - index of the initial node
 */
void statistic3(int initialNode) {
    queue<int> q;
    q.push(initialNode);

    while (!q.empty()) {
        if ((int)q.size() == 1) {
            cout << q.front()+1 << "\n";
        }

        int current = q.front();
        q.pop();

        int sonInserted = false;
        for(int adjacent: pipeline[current].operationsDependent) {
            if(pipeline[current].firstPass){
                pipeline[adjacent].dependencies -= 1;

                if (pipeline[adjacent].dependencies == 0) {
                    sonInserted = true;
                    q.push(adjacent);
                }
            }
            else if (pipeline[adjacent].dependencies == 0) {
                sonInserted = true;
            }
        }
        if ((int)pipeline[current].operationsDependent.size() == 0) {
            sonInserted = true;
        }
        pipeline[current].firstPass = false;

        if (!sonInserted) {
            q.push(current);
        }
    }
}



// Main
int main() {
    // N - number of operations
    int numOperations;
    cin >> numOperations;
    if (numOperations>=3 && numOperations<=1000) {  // limit: 3 <= N <= 1000
        bool disconectedNodes = false;
        int initialNodes = -1;
        int finalNodes = numOperations;
        pipeline.resize(numOperations);

        int totalTimeS1 = 0;  // auxiliary variable if statistic 1 is selected

        int i, j;
        for (i=0; i<numOperations; i++) {
            cin >> pipeline[i].time;
            totalTimeS1 += pipeline[i].time;
            if (pipeline[i].time>=1 && pipeline[i].time<=100) {  // limit: 1 <= T <= 100
                cin >> pipeline[i].dependencies;
                if (pipeline[i].dependencies > 0) {
                    for (j = 0; j < pipeline[i].dependencies; j++) {
                        int aux;
                        cin >> aux;
                        if (aux > numOperations) { // connected to an operation that doesn't exist, invalid
                            disconectedNodes = true;
                            break;
                        }
                        else {
                            aux = aux - 1;
                            pipeline[aux].operationsDependent.push_back(i);
                            if (pipeline[aux].isLast) {
                                pipeline[aux].isLast = false;
                                finalNodes -= 1;
                            }
                        }
                    }
                }
                else if (pipeline[i].dependencies == 0) {
                    if (initialNodes == -1) { // first initial node, save operation index
                        initialNodes = i;
                    }
                    else { // more than one possible initial node, invalid
                        initialNodes = -1;
                        break;
                    }
                }
            }
        }

        int statistic;  // S - statistic that should be computed
        cin >> statistic;

        if (validatePipeline(numOperations, disconectedNodes, initialNodes, finalNodes)) {
            if (statistic == 0) {
                cout << "VALID\n";
            }
            else if (statistic == 1) {
                statistic1(initialNodes, totalTimeS1);
            }
            else if (statistic == 2) {
                statistic2(numOperations, initialNodes);
            }
            else if (statistic == 3) {
                statistic3(initialNodes);
            }
        }
        else {
            cout << "INVALID\n";
        }
    }

    pipeline.clear();

    return 0;
}