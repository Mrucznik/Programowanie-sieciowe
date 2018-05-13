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
        private Fork leftFork;
        private Fork rightFork;

        public Filozof LeftFilozof;
        public Filozof RightFilozof;

        public Filozof(Fork leftFork, Fork rightFork)
        {
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public void Start()
        {
            while (true)
            {
                Eat(leftFork, rightFork);
            }
        }

        public void Eat(Fork leftFork, Fork rightFork)
        {
            Random rand = new Random();
            Thread.Sleep(rand.Next(5000));
        }
    }
}
