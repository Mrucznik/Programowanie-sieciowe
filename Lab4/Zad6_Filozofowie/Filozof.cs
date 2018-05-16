using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab4_Zad6_Filozofowie
{
    public class Filozof
    {
        private static int ID = 0;

        private int id;
        private int eatenMeals = 0;
        private string name;
        private Fork leftFork;
        private Fork rightFork;

        public Filozof(string name, Fork leftFork, Fork rightFork)
        {
            this.leftFork = leftFork;
            this.rightFork = rightFork;
            this.name = name;
            this.id = ID++;
        }

        public void Dine()
        {
            while (true)
            {
                Think();
                Eat(leftFork, rightFork);
            }
        }

        private void Think()
        {
            Console.WriteLine(name + " myśli...");
            
            Thread.Sleep(3000);

            Console.WriteLine(name + " skończył myśleć");
        }

        private void Eat(Fork leftFork, Fork rightFork)
        {
            Console.WriteLine(name + " je");

            Random rand = new Random();
            Thread.Sleep(rand.Next(5000));

            leftFork.Dirty = true;
            rightFork.Dirty = true;
            eatenMeals++;

            Console.WriteLine(name + " skończył jeść");
        }
    }
}
