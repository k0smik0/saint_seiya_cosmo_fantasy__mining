package net.iubris.optimus_saint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IteratorMain {
	
	private List<Integer> list;
	private Random random;

	private IteratorMain() {
		
		list = new ArrayList<>();
		for (int i=0;i<10000;i++) {
			list.add(i);
		}
//		Collections.shuffle(list);
		
		random = new Random(100);
	}
	
	public Stream<Integer> stream() {
		Iterator<Integer> iterator = list.iterator();
		Iterator<Integer> iteratorWrapper = new Iterator<Integer>() {
			private static final int MOD = 1000;
			private int iteratorIndex = 0;
			private int collected = 0;
			private boolean firstCollectedPrint;
			@Override
			public boolean hasNext() {
				if (iteratorIndex<MOD) {
					iteratorIndex++;
					return iterator.hasNext();
				}
				
				if (iteratorIndex%MOD==0) {
					System.out.print("waiting 1000ms: ");
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!firstCollectedPrint) {
						System.out.print("(collected: "+(iteratorIndex)+") ");
						firstCollectedPrint = true;
					} else {
						System.out.print("(collected: "+collected+") ");
					}
					collected = 0;
				}
				collected++;
				
				iteratorIndex++;
				return iterator.hasNext();
			}

			@Override
			public Integer next() {
				return iterator.next();
			}
		};
		
		Stream<Integer> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iteratorWrapper, Spliterator.ORDERED), false);
		
		return stream;
	}

	public static void main(String[] args) {
		
		IteratorMain main = new IteratorMain();
		
		main.stream().forEach(c->System.out.println(c));
		
	}
}
