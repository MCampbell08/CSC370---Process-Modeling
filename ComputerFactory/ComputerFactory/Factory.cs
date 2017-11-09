using System;
using System.Collections.Generic;
using System.Text;

namespace ComputerFactory
{
    public class Factory
    {
        private const double WorkingHours = 0.60 * 40; // 40 hours.
        private const float PlaceMotherboard = 0.10f, // 10 mins
                            PlaceProcessors = 0.02f, // 2 mins
                            PlaceHardDrive = 0.01f, //  1 min
                            PlaceRAM = 0.0075f;    //  .75 mins

        private double SimClock = 0;

        private List<ComputerPart> MotherboardQueue = new List<ComputerPart>();

        public void Run()
        {
            
        }
    }
}
