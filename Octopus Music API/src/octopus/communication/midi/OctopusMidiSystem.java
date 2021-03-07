package octopus.communication.midi;
/*
 *	MidiCommon.java
 *
 *	This file is part of jsresources.org
 */

import java.util.Vector;

/*
 * Copyright (c) 1999 - 2001 by Matthias Pfisterer
 * Copyright (c) 2003 by Florian Bomers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
 */

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;



/* Chand name and impove methods. Maybe Extend MidiSystem
 */
public class OctopusMidiSystem //extends MidiSystem
{
	/**	TODO:
                todo: flag long
	 */
	public static void listDevicesAndExit(boolean bForInput,
			boolean bForOutput)
	{
		listDevicesAndExit(bForInput, bForOutput, false);
	}



	public static MidiDevice[] getDevices(boolean bForInput,
			boolean bForOutput){
		Vector<MidiDevice> devices = new Vector<MidiDevice>();


		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++)
		{
			try
			{
				MidiDevice	device = MidiSystem.getMidiDevice(aInfos[i]);
				boolean		bAllowsInput = (device.getMaxTransmitters() != 0);
				boolean		bAllowsOutput = (device.getMaxReceivers() != 0);
				if ((bAllowsInput && bForInput) ||
						(bAllowsOutput && bForOutput))
				{

					devices.add(device);    
					//out("" + i + "  " + aInfos[i].getName());

				}
			}
			catch (MidiUnavailableException e)
			{
				// device is obviously not available...
				// out(e);
			}
		}

		MidiDevice[] deviceArray = (MidiDevice[]) devices.toArray(new MidiDevice[0]);
		return deviceArray;

	}

	public static void listDevicesAndExit(boolean bForInput, boolean bForOutput, boolean bVerbose)
	{
		listDevices(bForInput, bForOutput, bVerbose);
		System.exit(0);
	}

	public static void listDevices(boolean bForInput,
			boolean bForOutput,
			boolean bVerbose)
	{
		if (bForInput && !bForOutput)
		{
			out("Available MIDI IN Devices:");
		}
		else if (!bForInput && bForOutput)
		{
			out("Available MIDI OUT Devices:");
		}
		else
		{
			out("Available MIDI Devices:");
		}

		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++)
		{
			try
			{
				MidiDevice	device = MidiSystem.getMidiDevice(aInfos[i]);
				boolean		bAllowsInput = (device.getMaxTransmitters() != 0);
				boolean		bAllowsOutput = (device.getMaxReceivers() != 0);
				if ((bAllowsInput && bForInput) ||
						(bAllowsOutput && bForOutput))
				{
					if (bVerbose)
					{
						out("" + i + "  "
								+ (bAllowsInput?"IN ":"   ")
								+ (bAllowsOutput?"OUT ":"    ")
								+ aInfos[i].getName() + ", "
								+ aInfos[i].getVendor() + ", "
								+ aInfos[i].getVersion() + ", "
								+ aInfos[i].getDescription());
					}
					else
					{
						out("" + i + "  " + aInfos[i].getName());
					}
				}
			}
			catch (MidiUnavailableException e)
			{
				// device is obviously not available...
				// out(e);
			}
		}
		if (aInfos.length == 0)
		{
			out("[No devices available]");
		}		
	}



	/** Retrieve a MidiDevice.Info for a given name.

        This method tries to return a MidiDevice.Info whose name
        matches the passed name. If no matching MidiDevice.Info is
        found, null is returned.  If bForOutput is true, then only
        output devices are searched, otherwise only input devices.

        @param strDeviceName the name of the device for which an info
        object should be retrieved.

        @param bForOutput If true, only output devices are
        considered. If false, only input devices are considered.

        @return A MidiDevice.Info object matching the passed device
        name or null if none could be found.

	 */
	public static MidiDevice.Info getMidiDeviceInfo(String strDeviceName, boolean bForOutput)
	{
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++)
		{
			if (aInfos[i].getName().equals(strDeviceName))
			{
				try
				{
					MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
					boolean	bAllowsInput = (device.getMaxTransmitters() != 0);
					boolean	bAllowsOutput = (device.getMaxReceivers() != 0);
					if ((bAllowsOutput && bForOutput) || (bAllowsInput && !bForOutput))
					{
						return aInfos[i];
					}
				}
				catch (MidiUnavailableException e)
				{
					// TODO:
				}
			}
		}
		return null;
	}


	public static MidiDevice getMidiDevice(String deviceName) throws
	MidiUnavailableException {

		MidiDevice device=null;
		if (deviceName != null)
		{
			MidiDevice.Info info = OctopusMidiSystem.getMidiDeviceInfo(deviceName);
			device = MidiSystem.getMidiDevice(info);

		}
		return device;
	}


	public static MidiDevice openMidiDevice(String deviceName) throws
	MidiUnavailableException {

		/* MidiDevice device = MidiSystem.getMidiDevice( MidiSystem.getMidiDeviceInfo()[cbIndice -1]);
                 guitar = new YamahaEZAG(device.getDeviceInfo().getName(), "");*/
		MidiDevice device=null;
		if (deviceName != null)
		{
			MidiDevice.Info	info = OctopusMidiSystem.getMidiDeviceInfo(deviceName);
			//  if (info == null){

			//   System.out.println("no device info found for name " + deviceName);
			// }
			// try
			// {
			device = MidiSystem.getMidiDevice(info);
			device.open();
			// }
			// catch (Exception e)
			// {
			//     e.printStackTrace();
			// }
			// if (device == null)
			// {
			// System.out.println("wasn't able to retrieve MidiDevice");

			// }
		}
		return device;
	}


	public static MidiDevice.Info getMidiDeviceInfo(String strDeviceName)
	{
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++)
		{
			if (aInfos[i].getName().equals(strDeviceName))
			{
				return aInfos[i];

			}
		}
		return null;
	}

	/**
	 * Retrieve a MidiDevice.Info by index number.
	 * This method returns a MidiDevice.Info whose index
	 * is specified as parameter. This index matches the
	 * number printed in the listDevicesAndExit method.
	 * If index is too small or too big, null is returned.
	 *
	 * @param index the index of the device to be retrieved
	 * @return A MidiDevice.Info object of the specified index
	 *         or null if none could be found.
	 */
	public static MidiDevice.Info getMidiDeviceInfo(int index)
	{
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		if ((index < 0) || (index >= aInfos.length)) {
			return null;
		}
		return aInfos[index];
	}

	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}




