# 590-a2
Assignment 2: Java Threads 
Angelina León & Juha (Jules) Kim 

Explain your design rationale.
What features and structures are you using to represent…

#Philosophers
	Each philosopher is represented as a Java Thread where the ‘Philosopher’ Class is every instance of a philosopher present. Each philosopher has an id as well as left and right indices that represent the 2 forks needed to eat. Modeling philosophers as threads represents true concurrency, where each one runs independently in a loop of think ->eat -> repeat. 

#Table
	The table is an implicit shared environment containing the forks[] array, where indices represent a place/position at the table.

#Fork
	Each fork is a binary semaphore [new Semaphore(1,true)] with only 1 permit, meaning that only one possible philosopher can hold a fork and the true state verifies that the semaphore is fair and following a FIFO ordering. We chose a Semaphore because it represents a shared resource whose fairness setting reduces starvation risk by granting that forks are grabbed in order. 

#Spaghetti
	Similar to the Table’s implicit logic, the spaghetti is simulated through the act of “eating”. The spaghetti itself is conceptual and occurs in a given amount of time simulated by TimeUnit.MILLISECONDS.sleep(800);

#Eating Phase - eat()
	In this phase, the philosopher picks up the forks, sleeps (“eats”), and puts the forks down. This section is critical and where shared resources are needed. 

#Thinking Phase - think()
	Prints the philosopher's status and sleeps for a variable amount of time. This helps stagger philosopher timing while simulating a non-critical section.

What does your algorithm do to help prevent deadlocks and starvation? 
    - Deadlocks are prevented by breaking the circular chain of each philosopher holding a fork and waiting for the other. This is accomplished because not every philosopher picks up a fork in the same order. Since at least one philosopher tries forks in reverse order, the circular wait condition is disrupted.
    - Starvation is prevented through FIFO ordering achieved with the fair Semaphore that limits someone from always cutting in line. Thinking delays also stagger timing. 

Are deadlocks and/or starvations still possible (and just improbable)?
	Yes, starvation is still possible but extremely unlikely due to thread scheduling, while deadlocks are impossible due to a break in circular wait. 
