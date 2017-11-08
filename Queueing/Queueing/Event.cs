using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Queueing
{
    public class Event
    {
        public double BeginTime { get; set; }
        public double AverageTime { get; set; }
        public double ElapsedTime { get; set; }
        private double _timeToTake;
        public double TimeToEnd {
            get => _timeToTake + StartTime + AverageTime + BeginTime;
            set => _timeToTake = value;
        }
        public string HelloMessage { get; set; }
        public string EndingMessage { get; set; }
        public int ID { get; set; }
        public double StartTime { get; set; }
        public Action StartAction { get; set; }
        public Action EndAction { get; set; }
        public double FirstInvokeTimestamp { get; set; } = -1;
        public bool HasBeenAnnounced { get; set; } = false;
        private static Random random = new Random();
        public EventType EvType { get; set; } = EventType.Other;
        public enum EventType
        {
            HttpRequest, Other
        }

        public Event(EventType evType, double beginTime, double averageTime)
        {
            BeginTime = beginTime;
            AverageTime = averageTime;
            TimeToEnd = GetWaitTime(AverageTime);
            EvType = evType;
        }

        public Event(EventType evType, double beginTime, double averageTime, string helloMessage, string endingMessage, Action start, Action end)
        {
            BeginTime = beginTime;
            AverageTime = averageTime;
            TimeToEnd = GetWaitTime(AverageTime);
            HelloMessage = helloMessage;
            EndingMessage = endingMessage;
            StartAction = start;
            EndAction = end;
            EvType = evType;
        }

        public Event(EventType evType, double beginTime, double averageTime, string helloMessage, string endingMessage)
        {
            BeginTime = beginTime;
            AverageTime = averageTime;
            TimeToEnd = GetWaitTime(AverageTime);
            HelloMessage = helloMessage;
            EndingMessage = endingMessage;
            EvType = evType;
        }

        public Event() { }

        public void Start()
        {
            Console.WriteLine(HelloMessage);
            StartAction?.Invoke();
        }

        public void End()
        {
            if (EndingMessage != null)
                Console.WriteLine(EndingMessage);
            EndAction?.Invoke();
        }

        public static double GetWaitTime(double ms)
        {
            double randDouble = random.NextDouble();
            return Math.Abs(-Math.Log(1 - randDouble) - ms);
        }

        public bool PassesActivityCheck(Simulation context)
        {
            if (FirstInvokeTimestamp == -1)
            {
                FirstInvokeTimestamp = context.SimClock;
            }
            return (EvType == EventType.HttpRequest
                && context.ActiveEvents.Where(
                    e => e.EvType == EventType.HttpRequest
                    ).FirstOrDefault() == null);
        }
    }
}
