using System;
using System.Collections.Generic;
using System.Text;

namespace ComputerFactory
{
    public class Factory
    {
        private const double WorkingHours = 60 * 40; // 40 hours.
        private const float PlaceMotherboard = 10f, // 10 mins
                            PlaceProcessors = 2f, // 2 mins
                            PlaceHardDrive = 1f, //  1 min
                            PlaceRAMSticks = .75f;    //  .75 mins

        private float PlaceRandMotherboard = 10f,
                      PlaceRandProcessors = 2f,
                      PlaceRandHardDrive = 1f,
                      PlaceRandRAMSticks = .75f;

        private double SimClock = 0.00;

        private List<Computer> MotherboardQueue = new List<Computer>();
        private List<Computer> ProcessorQueue = new List<Computer>();
        private List<Computer> HardDriveQueue = new List<Computer>();
        private List<Computer> RAMSticksQueue = new List<Computer>();

        public List<Computer> ComputersFinished = new List<Computer>();

        private const int MotherboardWorkers = 6;
        private const int ProcessorWorkers = 3;
        private const int HardDriveWorkers = 2;
        private const int RAMWorkers = 2;

        private static double MotherboardLastEntry = 0;
        private static double ProcessorLastEntry = 10;
        private static double HardDriveLastEntry = 12;
        private static double RAMLastEntry = 12.75;

        private Random random = new Random();

        private void InitValues()
        {
            MotherboardLastEntry = 0;
            ProcessorLastEntry = 10;
            HardDriveLastEntry = 12;
            RAMLastEntry = 12.75;

            PlaceRandMotherboard = 10f;
            PlaceRandProcessors = 2f;
            PlaceRandHardDrive = 1f;
            PlaceRandRAMSticks = .75f;

            List<Computer> MotherboardQueue = new List<Computer>();
            List<Computer> ProcessorQueue = new List<Computer>();
            List<Computer> HardDriveQueue = new List<Computer>();
            List<Computer> RAMSticksQueue = new List<Computer>();
        }

        public void Run()
        {
            InitValues();
            while (SimClock < WorkingHours)
            {
                if (SimClock >= (MotherboardLastEntry + PlaceRandMotherboard))
                {
                    PlaceRandMotherboard = (float)Ln(PlaceMotherboard);
                    MotherboardLastEntry = SimClock;
                    AddMotherboards();
                    foreach (Computer comp in MotherboardQueue)
                    {
                        if (comp.FinishedTimeForMotherboard == 0) { comp.FinishedTimeForMotherboard = SimClock; }
                    }
                }
                if (SimClock >= (ProcessorLastEntry + PlaceRandProcessors) && MotherboardQueue.Count > 0)
                {
                    PlaceRandProcessors = (float)Ln(PlaceProcessors);
                    ProcessorLastEntry = SimClock;
                    PassMotherboard();
                    foreach (Computer comp in ProcessorQueue)
                    {
                        if (comp.FinishedTimeForProcessor == 0) { comp.FinishedTimeForProcessor = SimClock; }
                    }
                }
                if (SimClock >= (HardDriveLastEntry + PlaceRandHardDrive) && ProcessorQueue.Count > 0)
                {
                    PlaceRandHardDrive = (float)Ln(PlaceHardDrive);
                    HardDriveLastEntry = SimClock;
                    PassProcessor();
                    foreach (Computer comp in HardDriveQueue)
                    {
                        if (comp.FinishedTimeForHardDrive == 0) { comp.FinishedTimeForHardDrive = SimClock; }
                    }
                }
                if (SimClock >= (RAMLastEntry + PlaceRandRAMSticks) && HardDriveQueue.Count > 0)
                {
                    PlaceRandRAMSticks = (float)Ln(PlaceRAMSticks);
                    RAMLastEntry = SimClock;
                    PassHardDrive();
                    foreach (Computer comp in RAMSticksQueue)
                    {
                        if (comp.FinishedTimeForRAM == 0)
                        {
                            comp.FinishedTimeForRAM = SimClock;
                            ComputersFinished.Add(comp);
                        }
                    }
                }
                SimClock += 0.01;
            }
        }
        private void AddMotherboards()
        {
            int amount = MotherboardQueue.Count;
            for(int i = 0; i < MotherboardWorkers; i++)
            {
                MotherboardQueue.Add(new Computer() { StartTime = SimClock, WaitTimeForProcessor = 0, WaitTimeForHardDrive = 0, WaitTimeForRAM = 0, FinishedTimeForMotherboard = 0, FinishedTimeForHardDrive = 0, FinishedTimeForProcessor = 0 });
            }
        }
        private void PassMotherboard()
        {
            for (int i = 0; i < ProcessorWorkers; i++)
            {
                if (i < MotherboardQueue.Count) {
                    Computer computer = MotherboardQueue[i];
                    MotherboardQueue.Remove(computer);
                    computer.WaitTimeForProcessor = SimClock - computer.FinishedTimeForMotherboard;
                    ProcessorQueue.Add(computer);
                }
            }
        }
        private void PassProcessor()
        {
            for (int i = 0; i < HardDriveWorkers; i++)
            {
                if (i < ProcessorQueue.Count)
                {
                    Computer computer = ProcessorQueue[i];
                    ProcessorQueue.Remove(computer);
                    computer.WaitTimeForHardDrive = SimClock - computer.FinishedTimeForProcessor;
                    HardDriveQueue.Add(computer);
                }
            }
        }
        private void PassHardDrive()
        {
            for (int i = 0; i < RAMWorkers; i++)
            {
                if (i < HardDriveQueue.Count)
                {
                    Computer computer = HardDriveQueue[i];
                    HardDriveQueue.Remove(computer);
                    computer.WaitTimeForRAM = SimClock - computer.FinishedTimeForHardDrive;
                    RAMSticksQueue.Add(computer);
                }
            }
        }

        private double Ln(double ms)
        {
            double randDouble = random.NextDouble();
            return (-Math.Log(1 - randDouble) * ms);
        }
    }
}
