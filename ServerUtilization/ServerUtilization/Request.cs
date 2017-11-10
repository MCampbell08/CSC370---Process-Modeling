using System;
using System.Collections.Generic;
using System.Text;

namespace ServerUtilization
{
    public class Request
    {
        public double ArrivalTime { get; set; }
        public double ServiceTime { get; set; }
        public double WaitTime { get; set; }
    }
}
