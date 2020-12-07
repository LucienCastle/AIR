from copy import deepcopy as clone
from random import randint as randint

class Node:
    def __init__(self, name):
        self.name = name
        self.neighbours = set()
        self.colour = 0

class GraphColouring:
    def __init__(self):
        self.initialStateAdjacencyList = {"A": {"C", "D", "F", "J"},
                                          "B": {"D", "E", "F", "G"},
                                          "C": {"A", "E", "G", "H"},
                                          "D": {"A", "B", "H", "I"},
                                          "E": {"B", "C", "I", "J"},
                                          "F": {"A", "B", "K"},
                                          "G": {"B", "C", "K"},
                                          "H": {"C", "D", "K"},
                                          "I": {"D", "E", "K"},
                                          "J": {"E", "A", "K"},
                                          "K": {"F", "G", "H", "I", "J"}
                                          }

    def get_initial_graph(self):
        graph_details_dictionary = {}
        # creating graph nodes with their names and heuristic values
        for x in self.initialStateAdjacencyList:
            current_node = Node(name=x)
            graph_details_dictionary.update({x: current_node})

        for x in graph_details_dictionary:
            for y in self.initialStateAdjacencyList[x]:
                graph_details_dictionary[x].neighbours.add(graph_details_dictionary[y])
        graph_structure = (list(graph_details_dictionary.values()))
        return graph_structure


class State:
    def __init__(self, graph_structure, edges_number):
        self.graphStructure = graph_structure  
        self.edgesNumber = edges_number

    def evaluate_value(self):
        delta = 0
        for x in self.graphStructure:
            for y in x.neighbours:
                if x.colour != y.colour:
                    delta += 1
        delta /= self.edgesNumber
        return delta

    # calculate possible next moves(states) among neighbours state
    def possible_next_states(self, only_better_states=True):
        possible_next_states = []
        current_value = self.evaluate_value()
        for i in range(len(self.graphStructure)):
            new_state = clone(self)
            new_state2 = clone(self)
            increase_node_color(new_state.graphStructure[i])
            decrease_node_color(new_state2.graphStructure[i])
            if only_better_states:
                if new_state.evaluate_value() > current_value:
                    possible_next_states.append(new_state)
                if new_state2.evaluate_value() > current_value:
                    possible_next_states.append(new_state2)
            else:
                possible_next_states.extend([new_state, new_state2])
        return possible_next_states

    def print_state(self):
        for node in self.graphStructure:
            print(node.name, ":", node.colour, ",", end=" ")


def increase_node_color(node):
    node.colour = (node.colour + 1) % 7


def decrease_node_color(node):
    node.colour = (node.colour - 1) % 7


def simple_hill_climbing(initial_state):
    current_state = initial_state
    expanded_states = 0
    viewed_states = 0
    while True:
        # print(current_state.evaluate_value())
        next_states = current_state.possible_next_states()  # find all possible next states better than current one
        if len(next_states) == 0:
            break
        else:
            best_next_state = next_states[0]
            for state in next_states:
                if state.evaluate_value() > best_next_state.evaluate_value():
                    best_next_state = state
            current_state = best_next_state
            expanded_states += 1
            viewed_states += len(next_states)
        current_state.print_state()
        print()

    print("expanded states number : ", expanded_states)
    print("viewed states number : ", viewed_states)
    print("final state colours : ", end="")
    current_state.print_state()

def main():
    problem = GraphColouring()
    graph_structure = problem.get_initial_graph()
    
    edges_number = 0
    for x in graph_structure:
        edges_number += len(x.neighbours)
    edges_number /= 2
    
    initial_state = State(graph_structure=graph_structure, edges_number=edges_number)
    for k,v in problem.initialStateAdjacencyList.items():
    	print(k,"->",v)
    print()
    
    simple_hill_climbing(initial_state)

if __name__ == '__main__':
    main()

