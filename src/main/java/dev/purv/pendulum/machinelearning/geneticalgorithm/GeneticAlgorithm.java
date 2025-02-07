package dev.purv.pendulum.machinelearning.geneticalgorithm;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;

import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.mutator.Mutator;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.recombiner.Recombiner;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector.Selector;
import dev.purv.pendulum.machinelearning.geneticalgorithm.populationsupplier.PopulationSupplier;

public class GeneticAlgorithm <T extends  Individual<T>>{
    //Attributes
    private int size;
    private final int maxGenerations;
    private final Population<T> population;
    private final Selector<T> selector;
    private final Recombiner<T> recombiner;
    private final Mutator<T> mutator;
    private final IntConsumer getPreparator;
    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    //Constructor
    private GeneticAlgorithm(PopulationSupplier<T> popSup, int maxGen, Selector<T> sel, Recombiner<T> recombiner, Mutator<T> mut, IntConsumer getPreparator) {
        this.population = popSup.get();
        this.maxGenerations = maxGen + this.population.getSize();
        this.selector = sel;
        this.recombiner = recombiner;
        this.mutator = mut;
        this.getPreparator = getPreparator;
    }

    //Methods

    /**
     * Solve the genetic algorithm
     * @return the best individual after all the evolutions
     */
    public T solve(){
        validateShutdownState();
        evolute();
        return getBestAndShutdown();
    }

    /**
     * Check if genetic algorithm is shutdown or not
     */
    private void validateShutdownState(){
        if(shutdown.get()){
            throw new IllegalStateException("GeneticAlgorithm already shutdown");
        }
    }


    /**
     * Evolute the population till max generations
     */
    private void evolute(){
        for(int i = population.getGeneration(); i < maxGenerations; i++){
            nextGeneration();
            System.out.println("Generation: " + i);
        }
    }

    /**
     * Helper function to go to the  next generation
     */
    private void nextGeneration(){
        prepareNextEvolution();
        evoluteNextGeneration();
    }

    /**
     * Prepare teh population for the evolution step
     */
    private void prepareNextEvolution(){
        //Debug purposes
        getGetPreparator().ifPresent(p->p.accept(this.population.getGeneration()));
        this.population.calcFitnesses();
        this.population.incrementGeneration();
    }

    /**
     * Evolute to the next generation of the population
     */
    private void evoluteNextGeneration(){
        //Selection step
        selector.select(population);
        //recombination step
        getRecombiner().ifPresent(r->r.recombine(this.population, this.size));
        this.size = this.population.getSize();
        //Mutate
        getMutator().ifPresent(m->m.mutate(this.population));
    }

    /**
     * @return the getPreparator if it exists
     */
    public Optional<IntConsumer> getGetPreparator(){
        return Optional.ofNullable(getPreparator);
    }

    /**
     * @return the recombiner of this algorithm if it exists
     */
    public Optional<Recombiner<T>> getRecombiner(){
        return Optional.ofNullable(recombiner);
    }

    /**
     * @return the Mutator of this algorithm if it exists
     */
    public Optional<Mutator<T>> getMutator(){
        return Optional.ofNullable(mutator);
    }

    /**
     * Final step of genetic algorithm
     * Get the best Individual after the evolution and shutdown alg
     * @return the best individual
     */
    private T getBestAndShutdown(){
        this.population.calcFitnesses();
        T best = this.population.getBestT();
        this.shutdown.set(true);
        return best;
    }

    //Builder pattern
    public static class Builder<T extends Individual<T>>{

        //Attributes
        private final int maxGenerations;
        private final PopulationSupplier<T> popSupplier;
        private final Selector<T> selector;
        private Recombiner<T> recombiner;
        private Mutator<T> mutator;
        private IntConsumer genPreperator;

        //Constructor
        public Builder(int maxGenerations, PopulationSupplier<T> popSupplier, Selector<T> selector){
            this.maxGenerations = maxGenerations;
            this.popSupplier = popSupplier;
            this.selector = selector;
        }

        //Methods
        //Set the parameters for the builder
        public Builder<T> withRecombiner(Recombiner<T> recombiner){
            this.recombiner = recombiner;
            return this;
        }

        public Builder<T> withMutator(Mutator<T> mutator){
            this.mutator = mutator;
            return this;
        }

        public Builder<T> withPreperator(IntConsumer preperator){
            this.genPreperator = preperator;
            return this;
        }

        //Building patter
        public GeneticAlgorithm<T> build(){
            return new GeneticAlgorithm<T>(this.popSupplier, this.maxGenerations, this.selector, this.recombiner, this.mutator, this.genPreperator);
        }


    }


}
