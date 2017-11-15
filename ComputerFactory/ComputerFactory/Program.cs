using System;
using System.Collections.Generic;

namespace ComputerFactory
{
    class Program
    {
        private const int AmountOfRuns = 1000;
        static void Main(string[] args)
        {
            List<Factory> factories = new List<Factory>();
            Factory factory = null;
            for (int i = 0; i < AmountOfRuns; i++)
            {
                factory = new Factory();
                factory.Run();
                factories.Add(factory);
            }
            int amount = 0;
            foreach (Factory f in factories)
            {
                amount += f.ComputersFinished.Count;
            }
            amount /= AmountOfRuns;
            Console.WriteLine("Amount of Runs For This Program: " + AmountOfRuns + " | Average Computers Made: " + amount);

        }
    }
}
