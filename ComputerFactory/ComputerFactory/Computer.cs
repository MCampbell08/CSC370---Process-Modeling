using System;
using System.Collections.Generic;
using System.Text;

namespace ComputerFactory
{
    public class Computer
    {
        public double StartTime { get; set; }
        public double FinishedTimeForMotherboard { get; set; }
        public double FinishedTimeForProcessor { get; set; }
        public double FinishedTimeForHardDrive { get; set; }
        public double FinishedTimeForRAM { get; set; }
        public double WaitTimeForProcessor { get; set; }
        public double WaitTimeForHardDrive { get; set; }
        public double WaitTimeForRAM { get; set; }
    }
}
