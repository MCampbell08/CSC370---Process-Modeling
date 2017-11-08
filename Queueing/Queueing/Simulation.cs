using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static Queueing.Event;

namespace Queueing
{
    public class Simulation
    {
        public List<Event> ActiveEvents = new List<Event>();
        public List<Event> FutureEvents = new List<Event>();
        private const double MaxOpenTime = 0.60*8; // 8 hours.
        private const float ArrivalTime  = 0.18f, // 18 mins
                            FitTime      = 0.10f, // 10 mins
                            TryOnTime    = 0.08f, //  8 mins
                            PurchaseTime = 0.02f; //  2 mins
        public Simulation()
        {
            for (int i = 0; i < 30; i++)
            {
                FutureEvents.Add(new Event(EventType.HttpRequest, Ln(ArrivalTime)*i, 0, "Customer is being seen by the tailor.", null, 
                    () => {
                        activeEventsToAdd.Add(new Event(EventType.HttpRequest, (Ln(ArrivalTime) * i) + Ln(FitTime), 0, "Customer is being fitted.", null, null, () => {
                            activeEventsToAdd.Add(new Event(EventType.HttpRequest, (Ln(ArrivalTime) * i) + Ln(FitTime) + Ln(TryOnTime), 0, "Customer is trying on suits that fit their measurements.", null, null, () =>{
                                activeEventsToAdd.Add(new Event(EventType.HttpRequest, (Ln(ArrivalTime) * i) + Ln(PurchaseTime) + Ln(FitTime) + Ln(TryOnTime), 0, "Customer is purchasing the suit they picked.", "Customer is leaving."));
                            }));
                        }));
                    }, null));
            }
        }

        private List<Event> activeEventsToAdd = new List<Event>();
        private List<double> waitTimes = new List<double>();
        public double SimClock = 0;
        private static Random random = new Random();

        public static double Ln(double ms)
        {
            double randDouble = random.NextDouble();
            return Math.Abs(-Math.Log(1 - randDouble) - ms);
        }

        public void Run()
        {
            while (SimClock < MaxOpenTime)
            {
                List<Event> futureEventsToRemove = new List<Event>();
                List<Event> activeEventsToRemove = new List<Event>();
                foreach (Event e in ActiveEvents)
                {
                    if (e.ElapsedTime++ >= e.TimeToEnd)
                    {
                        activeEventsToRemove.Add(e);
                        if (e.EndingMessage != null)
                            Console.Write($"Time [{ConvertToTime(SimClock)}] : ");
                        e.End();
                    }
                    if (!e.HasBeenAnnounced && e.BeginTime + e.StartTime <= e.ElapsedTime)
                    {
                        Console.Write($"Time [{ConvertToTime(SimClock)}] : ");
                        e.Start();
                        e.HasBeenAnnounced = true;
                    }
                }
                foreach (Event e in FutureEvents)
                {
                    if (e.BeginTime <= SimClock)
                    {
                        if (e.PassesActivityCheck(this))
                        {
                            ActiveEvents.Add(e);
                            futureEventsToRemove.Add(e);
                            e.StartTime = SimClock;
                            Console.Write($"Time [{ConvertToTime(SimClock)}] : ");
                            e.Start();
                            e.HasBeenAnnounced = true;
                            waitTimes.Add(SimClock - e.FirstInvokeTimestamp);
                        }
                    }
                }
                activeEventsToRemove.ForEach(e => ActiveEvents.Remove(e));
                futureEventsToRemove.ForEach(e => FutureEvents.Remove(e));
                activeEventsToAdd.ForEach(e => ActiveEvents.Add(e));
                activeEventsToAdd.Clear();
                SimClock += 0.01;
            }
            double waitAverage = 0;
            waitTimes.ForEach(w => waitAverage += w);
            waitAverage /= waitTimes.Count;
            Console.WriteLine("Average wait time: " + ConvertToTime(waitAverage));
        }

        public static string ConvertToTime(double time)
        {
            int m = 0, h = 0;
            while (time >= 0.6)
            {
                h++;
                time -= 0.6;
            }
            m = (int)(time*100);
            return $"{h:00}:{m:00}";
        }
    }
}
