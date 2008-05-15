/*
 * This file is part of org.kalmeo.kuix.
 * 
 * org.kalmeo.kuix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.kalmeo.kuix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with org.kalmeo.kuix.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Creation date : 21 nov. 2007
 * Copyright (c) Kalmeo 2007-2008. All rights reserved.
 * http://www.kalmeo.org
 */

package org.kalmeo.kuix.util;

/**
 * @author bbeaulant
 */
public class Gap {

	public int horizontalGap;
	public int verticalGap;

	/**
	 * Construct a {@link Gap}
	 */
	public Gap() {
	}

	/**
	 * Construct a {@link Gap}
	 * 
	 * @param horizontalGap
	 * @param verticalGap
	 */
	public Gap(int horizontalGap, int verticalGap) {
		this.horizontalGap = horizontalGap;
		this.verticalGap = verticalGap;
	}

}
