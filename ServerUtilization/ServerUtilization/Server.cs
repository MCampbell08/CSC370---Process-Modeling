using System;
using System.Collections.Generic;

namespace ServerUtilization
{
    public class Server
    {
        private List<Request> RequestsVisiting = new List<Request>();
        private const float ArrivalTime = 150f, // 20 millis
                            ProcessingTime = 15f; // 15 millis
        private const int AmountOfRequests = 10000;

        private static Random random = new Random();

        private double simClock = 0.0;

        private void CreateCustomers()
        {
            double arrivalTimesTogether = 0.0;
            for (int i = 0; i < AmountOfRequests; i++)
            {
                Request request = new Request
                {
                    ArrivalTime = (arrivalTimesTogether += Ln(ArrivalTime)),
                    ServiceTime = Ln(ProcessingTime)
                };
                RequestsVisiting.Add(request);
            }
        }

        public void Run()
        {
            CreateCustomers();
            for (int i = 0; i < RequestsVisiting.Count; i++)
            {
                Request req = RequestsVisiting[i];
                if (simClock <= req.ArrivalTime)
                {
                    simClock = req.ArrivalTime + req.ServiceTime;
                }
                else
                {
                    req.WaitTime = simClock - req.ArrivalTime;
                    simClock += req.ServiceTime;
                }
            }
            double averageWaitTime = 0.0;
            foreach(Request req in RequestsVisiting)
            {
                averageWaitTime += req.WaitTime;
            }
            averageWaitTime /= AmountOfRequests;
            Console.WriteLine("Average Wait Time: " + averageWaitTime);
        }
        private double Ln(double ms)
        {
            double randDouble = random.NextDouble();
            return (-Math.Log(1 - randDouble) * ms);
        }
    }
}
