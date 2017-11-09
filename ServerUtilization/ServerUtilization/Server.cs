using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace ServerUtilization
{
    public class Server
    {
        private List<double> waitTimeRecorded = new List<double>();
        private const float ArrivalTime = 0.0020f, // 20 millis
                            ProcessingTime = 0.0015f; // 15 millis
        private const int AmountOfRequests = 1000;

        private static Random random = new Random();

        public void Run()
        {
            for (int i = 0; i < AmountOfRequests; i++)
            {
                double waitTime = ((Ln(ArrivalTime) * i) + Ln(ProcessingTime));
                waitTimeRecorded.Add(waitTime);

                Console.WriteLine("Processing Request | Time in: " + waitTime * 100);
                Thread.Sleep((int)waitTime);
            }
            double averageWaitTime = 0.0;

            foreach (double time in waitTimeRecorded)
            {
                averageWaitTime += time;
            }
            averageWaitTime /= waitTimeRecorded.Count;
            averageWaitTime *= 100;
            Console.WriteLine("Average Wait Time: " + averageWaitTime);
        }
        private double Ln(double ms)
        {
            double randDouble = random.NextDouble();
            return (-Math.Log10(1 - randDouble) * ms);
        }
    }
}
