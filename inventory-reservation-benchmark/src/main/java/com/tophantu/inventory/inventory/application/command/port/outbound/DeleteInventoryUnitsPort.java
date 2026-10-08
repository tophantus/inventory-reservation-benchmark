package com.tophantu.inventory.inventory.application.command.port.outbound;

import java.util.List;

public interface DeleteInventoryUnitsPort {

    void deleteByIds(List<Long> unitIds);
}
